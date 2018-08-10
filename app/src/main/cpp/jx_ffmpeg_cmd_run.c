/**
 * Created by jianxi on 2017/6/4..
 * https://github.com/mabeijianxi
 * mabeijianxi@gmail.com
 */

#include "ffmpeg.h"
#include <jni.h>
#include "com_mabeijianxi_jianxiffmpegcmd_Test.h"
#include "com_zph_cvideo_ffmpeng_FfmpegTool.h"
#include "fram_rotate.h"
#include <android/log.h>

#define  LOG_TAG    "ImageEncf"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

JNIEXPORT jint JNICALL
Java_com_zph_cvideo_ffmpeng_FfmpegTool_cmdRun(JNIEnv *env, jobject type,
                                        jobjectArray commands) {
    int argc = (*env)->GetArrayLength(env, commands);
    char *argv[argc];
    int i;
    for (i = 0; i < argc; i++) {
        jstring js = (jstring) (*env)->GetObjectArrayElement(env, commands, i);
        argv[i] = (char *) (*env)->GetStringUTFChars(env, js, 0);
    }
    LOGD("Java_com_zph_cvideo_ffmpeng_FfmpegTool_cmdRun:");
    return jxRun(argc, argv);
}

JNIEXPORT jstring JNICALL
Java_com_mabeijianxi_jianxiffmpegcmd_MainActivity_getFFmpegConfig(JNIEnv *env, jobject instance) {

    char info[10000] = {0};
    sprintf(info, "%s\n", avcodec_configuration());
    return (*env)->NewStringUTF(env, info);

}

JNIEXPORT jint JNICALL Java_com_zph_cvideo_ffmpeng_FfmpegTool_decodToImage
        (JNIEnv *env, jclass mclass, jstring in, jstring dir, jint startTime, jint num) {

    char *input = jstringTostring(env, in);
    char *parent = jstringTostring(env, dir);

    LOGD("input:%s", input);
    LOGD("parent:%s", parent);


    av_register_all();
    AVFormatContext *pFormatCtx = avformat_alloc_context();
    // Open video file
    if (avformat_open_input(&pFormatCtx, input, NULL, NULL) != 0) {
        LOGD("Couldn't open file:%s\n", input);
        return -1; // Couldn't open file
    }

    // Retrieve stream information
    if (avformat_find_stream_info(pFormatCtx, NULL) < 0) {
        LOGD("Couldn't find stream information.");
        return -1;
    }
    // Find the first video stream
    int videoStream = -1, i;
    for (i = 0; i < pFormatCtx->nb_streams; i++) {
        if (pFormatCtx->streams[i]->codec->codec_type == AVMEDIA_TYPE_VIDEO
            && videoStream < 0) {
            videoStream = i;
        }
    }
    if (videoStream == -1) {
        LOGD("Didn't find a video stream.");
        return -1; // Didn't find a video stream
    }

    // Get a pointer to the codec context for the video stream
    AVCodecContext *pCodecCtx = pFormatCtx->streams[videoStream]->codec;

    // Find the decoder for the video stream
    AVCodec *pCodec = avcodec_find_decoder(pCodecCtx->codec_id);
    if (pCodec == NULL) {
        LOGD("Codec not found.");
        return -1; // Codec not found
    }

    if (avcodec_open2(pCodecCtx, pCodec, NULL) < 0) {
        LOGD("Could not open codec.");
        return -1; // Could not open codec
    }
    // Allocate video frame
    AVFrame *pFrame = av_frame_alloc();

    if (pFrame == NULL) {
        LOGD("Could not allocate video frame.");
        return -1;
    }
    int64_t count = startTime;
    int frameFinished;
    //(*pCodecCtx).
    AVPacket packet;
    AVDictionaryEntry *m = NULL;
    while (m = av_dict_get(pFormatCtx->metadata, "", m, AV_DICT_IGNORE_SUFFIX)) {
        LOGD("key:%s    value:%s", m->key, m->value);
    }

    /*
    LOGD("start_time:%d",pFormatCtx->start_time);
    LOGD("pFormatCtx den:%d", pFormatCtx->streams[videoStream]->sample_aspect_ratio.den);
    LOGD("pFormatCtx num:%d", pFormatCtx->streams[videoStream]->sample_aspect_ratio.num);
     * AVDictionaryEntry *tag = NULL;
     tag = av_dict_get(pFormatCtx->streams[videoStream]->metadata, "rotate", tag, 0);
     if (tag != NULL){
         LOGD("tag key:%s",tag->key);
         LOGD("tag value:%s",tag->value);

     } else{
         LOGD("tag==null");
     }*/
    av_seek_frame(pFormatCtx, -1, count * AV_TIME_BASE, AVSEEK_FLAG_BACKWARD);
    double rotate=get_rotation(pFormatCtx->streams[videoStream]);
    LOGD("rotate:%d",rotate);
    while (av_read_frame(pFormatCtx, &packet) >= 0) {
        if (packet.stream_index == videoStream) {
            // Decode video frame
            avcodec_decode_video2(pCodecCtx, pFrame, &frameFinished, &packet);
            // 并不是decode一次就可解码出一帧
            if (frameFinished) {
                if (count < (startTime + num)) {
                    if(pFrame->width>=800){
                        AVFrame *dst_picture = av_frame_clone(pFrame);

                        ScaleImg(pCodecCtx, pFrame, dst_picture, pFrame->height / 2,
                                 pFrame->width / 2);
                        MyWriteJPEG(dst_picture, parent, dst_picture->width,
                                    dst_picture->height, count);
                    } else{
                        MyWriteJPEG(pFrame, parent, pFrame->width,
                                    pFrame->height, count);
                    }
                    ++count;
                    av_seek_frame(pFormatCtx, -1, count * AV_TIME_BASE, AVSEEK_FLAG_BACKWARD);
                } else {
                    av_packet_unref(&packet);
                    LOGD("break:count:%d   startTime:%d  num:%d", count, startTime, num);
                    break;
                }

            }
        }
        av_packet_unref(&packet);
    }
    LOGD(":count:%d   startTime:%d  num:%d ", count, startTime, num);
    // Free the YUV frame
    av_free(pFrame);
    // Close the codecs
    avcodec_close(pCodecCtx);
    // Close the video file
    avformat_close_input(&pFormatCtx);
    return 0;
}



/**
 * 带回调
 * @param env
 * @param mclass
 * @param in
 * @param dir
 * @param startTime
 * @param num
 * @return
 */
JNIEXPORT jint JNICALL Java_com_zph_cvideo_ffmpeng_FfmpegTool_decodToImageWithCall
        (JNIEnv *env, jobject mclass, jstring in, jstring dir, jint startTime, jint num) {
    char *input = jstringTostring(env, in);
    char *parent = jstringTostring(env, dir);
    jclass clazz =(*env)->FindClass(env,"com/zph/cvideo/ffmpeng/FfmpegTool");
    if(clazz == 0){
        LOGD("find class error");
        return -1;
    }
    jmethodID methodId=(*env)->GetMethodID(env,clazz,"decodToImageCall","(Ljava/lang/String;I)V");

    if(methodId == NULL){
        LOGD("find methodId error");
    }
    LOGD("input:%s", input);
    LOGD("parent:%s", parent);
    av_register_all();
    AVFormatContext *pFormatCtx = avformat_alloc_context();
    // Open video file
    if (avformat_open_input(&pFormatCtx, input, NULL, NULL) != 0) {
        LOGD("Couldn't open file:%s\n", input);
        return -1; // Couldn't open file
    }

    // Retrieve stream information
    if (avformat_find_stream_info(pFormatCtx, NULL) < 0) {
        LOGD("Couldn't find stream information.");
        return -1;
    }
    // Find the first video stream
    int videoStream = -1, i;
    for (i = 0; i < pFormatCtx->nb_streams; i++) {
        if (pFormatCtx->streams[i]->codec->codec_type == AVMEDIA_TYPE_VIDEO
            && videoStream < 0) {
            videoStream = i;
        }
    }
    if (videoStream == -1) {
        LOGD("Didn't find a video stream.");
        return -1; // Didn't find a video stream
    }
    // Get a pointer to the codec context for the video stream
    AVCodecContext *pCodecCtx = pFormatCtx->streams[videoStream]->codec;
    // Find the decoder for the video stream
    AVCodec *pCodec = avcodec_find_decoder(pCodecCtx->codec_id);
    if (pCodec == NULL) {
        LOGD("Codec not found.");
        return -1; // Codec not found
    }

    if (avcodec_open2(pCodecCtx, pCodec, NULL) < 0) {
        LOGD("Could not open codec.");
        return -1; // Could not open codec
    }
    // Allocate video frame
    AVFrame *pFrame = av_frame_alloc();

    if (pFrame == NULL) {
        LOGD("Could not allocate video frame.");
        return -1;
    }
    int64_t count = startTime;
    int frameFinished;
    //(*pCodecCtx).
    AVPacket packet;
    /*AVDictionaryEntry *m = NULL;
    while (m = av_dict_get(pFormatCtx->metadata, "", m, AV_DICT_IGNORE_SUFFIX)) {
        LOGD("key:%s    value:%s", m->key, m->value);
    }*/
    av_seek_frame(pFormatCtx, -1, count * AV_TIME_BASE, AVSEEK_FLAG_BACKWARD);
    /*double rotate=get_rotation(pFormatCtx->streams[videoStream]);
    LOGD("rotate:%d",rotate);*/
    while (av_read_frame(pFormatCtx, &packet) >= 0) {
        if (packet.stream_index == videoStream) {
            // Decode video frame
            avcodec_decode_video2(pCodecCtx, pFrame, &frameFinished, &packet);
            // 并不是decode一次就可解码出一帧
            if (frameFinished) {
                if (count < (startTime + num)) {
                    char* out_file=NULL;

                    if(pFrame->width>=800){
                        AVFrame *dst_picture = av_frame_clone(pFrame);
                        ScaleImg(pCodecCtx, pFrame, dst_picture, pFrame->height / 2,
                                 pFrame->width / 2);
                        out_file=MyWriteJPEG2(dst_picture,parent, dst_picture->width,
                                              dst_picture->height,count);
                       av_frame_free(&dst_picture);
                    } else{
                        out_file=MyWriteJPEG2(pFrame,parent, pFrame->width,
                                    pFrame->height,count);
                    }

                    (*env)->CallVoidMethod(env,mclass,methodId,(*env)->NewStringUTF(env,out_file),count);
                    ++count;
                    free(out_file);
                    av_seek_frame(pFormatCtx, -1, count * AV_TIME_BASE, AVSEEK_FLAG_BACKWARD);
                } else {
                    av_packet_unref(&packet);
                    break;
                }

            }
        }
        av_packet_unref(&packet);
    }
    LOGD("开始释放内存");
    LOGD("DeleteLocalRef. clazz");
    (*env)->DeleteLocalRef(env,clazz);
    free(input);
    free(parent);
    // Free the YUV frame
    av_free(pFrame);
    // Close the codecs
    avcodec_close(pCodecCtx);
    // Close the video file
    avformat_close_input(&pFormatCtx);

    return 0;
}




int ScaleImg(AVCodecContext *pCodecCtx, AVFrame *src_picture, AVFrame *dst_picture, int nDstH,
             int nDstW) {
    LOGD("-----------ScaleImg------------");
    int nSrcStride[3];
    int nSrcH = pCodecCtx->height;
    int nSrcW = pCodecCtx->width;
    struct SwsContext *m_pSwsContext;

    nSrcStride[0] = nSrcW;
    nSrcStride[1] = nSrcW / 2;
    nSrcStride[2] = nSrcW / 2;

    dst_picture->linesize[0] = nDstW;
    dst_picture->linesize[1] = nDstW / 2;
    dst_picture->linesize[2] = nDstW / 2;

    dst_picture->width = nDstW;
    dst_picture->height= nDstH;

    m_pSwsContext = sws_getContext(nSrcW, nSrcH, src_picture->format,
                                   nDstW, nDstH, src_picture->format,
                                   SWS_BICUBIC,
                                   NULL, NULL, NULL);

    if (NULL == m_pSwsContext) {
        printf("ffmpeg get context error!\n");
        exit(-1);
    }
    int ret=sws_scale(m_pSwsContext, src_picture->data, src_picture->linesize, 0, pCodecCtx->height,
              dst_picture->data, dst_picture->linesize);

    LOGD("line0:%d line1:%d line2:%d\n", dst_picture->linesize[0], dst_picture->linesize[1],
           dst_picture->linesize[2]);
    //LOGD("-__ret:%d  ,dst_picture:%d   ,src_picture:%d",ret, sizeof(dst_picture),sizeof(src_picture));
    sws_freeContext(m_pSwsContext);
    return 1;
}

char * MyWriteJPEG2(AVFrame *pFrame,char *path, int width, int height,int iIndex) {
    LOGD("----------MyWriteJPEG2 width:%d  height:%d  iIndex:%d", width, height,iIndex);
    // 输出文件路径
    char *out_file = malloc(1000);

    //LOGD("path:%s", path);
    sprintf(out_file, "%stemp%d.jpg", path, iIndex);
    //LOGD("----------MyWriteJPEG width:%d  height:%d  out_file:%s", width, height,out_file);
    // 分配AVFormatContext对象
    AVFormatContext *pFormatCtx = avformat_alloc_context();
    // 设置输出文件格式
    pFormatCtx->oformat = av_guess_format("mjpeg", NULL, NULL);
    // 创建并初始化一个和该url相关的AVIOContext
    if (avio_open(&pFormatCtx->pb, out_file, AVIO_FLAG_READ_WRITE) < 0) {
        LOGD("Couldn't open output file.");
        return -1;
    }

    // 构建一个新stream
    AVStream *pAVStream = avformat_new_stream(pFormatCtx, 0);
    if (pAVStream == NULL) {
        return -1;
    }



    // 设置该stream的信息
    AVCodecContext *pCodecCtx = pAVStream->codec;

    pCodecCtx->codec_id = pFormatCtx->oformat->video_codec;
    pCodecCtx->codec_type = AVMEDIA_TYPE_VIDEO;
    pCodecCtx->pix_fmt = AV_PIX_FMT_YUVJ420P;
    pCodecCtx->width = width;
    pCodecCtx->height = height;
    pCodecCtx->time_base.num = 1;
    pCodecCtx->time_base.den = 25;


    // Begin Output some information
    av_dump_format(pFormatCtx, 0, out_file, 1);
    // End Output some information
    // 查找解码器
    AVCodec *pCodec = avcodec_find_encoder(pCodecCtx->codec_id);


    if (!pCodec) {
        LOGD("Codec not found.");
        return -1;
    }
    // 设置pCodecCtx的解码器为pCodec
    if (avcodec_open2(pCodecCtx, pCodec, NULL) < 0) {
        LOGD("Could not open codec.");
        return -1;
    }
    //Write Header
    avformat_write_header(pFormatCtx, NULL);
    int y_size = pCodecCtx->width * pCodecCtx->height;
    //Encode
    // 给AVPacket分配足够大的空间
    AVPacket pkt;
    av_new_packet(&pkt, y_size * 3);
    //
    int got_picture = 0;
    int ret = avcodec_encode_video2(pCodecCtx, &pkt, pFrame, &got_picture);
    if (ret < 0) {
        LOGD("Encode Error.\n");
        return -1;
    }
    if (got_picture == 1) {
        //pkt.stream_index = pAVStream->index;
        ret = av_write_frame(pFormatCtx, &pkt);
    }
    av_free_packet(&pkt);

    //Write Trailer
    av_write_trailer(pFormatCtx);
    LOGD("Encode Successful.out_file:%s", out_file);

    if (pAVStream) {
        avcodec_close(pAVStream->codec);
    }
    avio_close(pFormatCtx->pb);
    avformat_free_context(pFormatCtx);

    return out_file;
}

/**
 * 将AVFrame(YUV420格式)保存为JPEG格式的图片
 *
 * @param width YUV420的宽
 * @param height YUV42的高
 *
 */
int MyWriteJPEG(AVFrame *pFrame, char *path, int width, int height, int iIndex) {
    LOGD("----------MyWriteJPEG width:%d  height:%d  iIndex:%d", width, height,iIndex);
    // 输出文件路径
    char out_file[1000] = {0};

    //LOGD("path:%s", path);
    sprintf(out_file, "%stemp%d.jpg", path, iIndex);
    //LOGD("out_file:%s", out_file);
    // 分配AVFormatContext对象
    AVFormatContext *pFormatCtx = avformat_alloc_context();

    // 设置输出文件格式
    pFormatCtx->oformat = av_guess_format("mjpeg", NULL, NULL);
    // 创建并初始化一个和该url相关的AVIOContext
    if (avio_open(&pFormatCtx->pb, out_file, AVIO_FLAG_READ_WRITE) < 0) {
        LOGD("Couldn't open output file.");
        return -1;
    }

    // 构建一个新stream
    AVStream *pAVStream = avformat_new_stream(pFormatCtx, 0);
    if (pAVStream == NULL) {
        return -1;
    }

    // 设置该stream的信息
    AVCodecContext *pCodecCtx = pAVStream->codec;

    pCodecCtx->codec_id = pFormatCtx->oformat->video_codec;
    pCodecCtx->codec_type = AVMEDIA_TYPE_VIDEO;
    pCodecCtx->pix_fmt = AV_PIX_FMT_YUVJ420P;
    pCodecCtx->width = width;
    pCodecCtx->height = height;
    pCodecCtx->time_base.num = 1;
    pCodecCtx->time_base.den = 25;


    // Begin Output some information
    av_dump_format(pFormatCtx, 0, out_file, 1);
    // End Output some information
    // 查找解码器
    AVCodec *pCodec = avcodec_find_encoder(pCodecCtx->codec_id);
    if (!pCodec) {
        LOGD("Codec not found.");
        return -1;
    }
    // 设置pCodecCtx的解码器为pCodec
    if (avcodec_open2(pCodecCtx, pCodec, NULL) < 0) {
        LOGD("Could not open codec.");
        return -1;
    }
    //Write Header
    avformat_write_header(pFormatCtx, NULL);
    int y_size = pCodecCtx->width * pCodecCtx->height;
    //Encode
    // 给AVPacket分配足够大的空间
    AVPacket pkt;
    av_new_packet(&pkt, y_size * 3);
    //
    int got_picture = 0;
    int ret = avcodec_encode_video2(pCodecCtx, &pkt, pFrame, &got_picture);
    if (ret < 0) {
        LOGD("Encode Error.\n");
        return -1;
    }
    if (got_picture == 1) {
        //pkt.stream_index = pAVStream->index;
        ret = av_write_frame(pFormatCtx, &pkt);
    }
    av_free_packet(&pkt);

    //Write Trailer
    av_write_trailer(pFormatCtx);
    LOGD("Encode Successful.out_file:%s", out_file);

    if (pAVStream) {
        avcodec_close(pAVStream->codec);
    }
    avio_close(pFormatCtx->pb);
    avformat_free_context(pFormatCtx);

    return 0;
}


char *jstringTostring(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    jclass clsstring = (*env)->FindClass(env, "java/lang/String");
    jstring strencode = (*env)->NewStringUTF(env, "utf-8");
    jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, jstr, mid, strencode);
    jsize alen = (*env)->GetArrayLength(env, barr);
    jbyte *ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1);

        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    (*env)->ReleaseByteArrayElements(env, barr, ba, 0);
    return rtn;
}
