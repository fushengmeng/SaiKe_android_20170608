#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <arpa/inet.h>
#include <errno.h>
#include <linux/unistd.h>
#include <pthread.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <termios.h>
#include <sys/ioctl.h>
#include <sys/select.h>
#include <sys/time.h>
#include <linux/input.h>
#include <android/log.h>
#include <math.h>
#include <fcntl.h>
#include <sys/epoll.h>


#define LOG_TAG "serialAPI"
#undef  LOG
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)

static int uart_fd;
static int         epoll_fd;

//配置串口参数
int set_opt(int fd, int nSpeed, int nBits, char nEvent, int nStop) {
	struct termios newtio, oldtio;
	if (tcgetattr(fd, &oldtio) != 0) {
		perror("SetupSerial 1");
		return -1;
	}
	bzero(&newtio, sizeof(newtio));
	newtio.c_cflag &= ~CSTOPB;
	        newtio.c_cflag &= ~CSIZE;
	        newtio.c_cflag |= (CLOCAL | CREAD);
	        newtio.c_cflag &= ~CRTSCTS;

	        /* set no software stream control */
	        newtio.c_iflag &= ~(IXON | INLCR | ICRNL | IGNCR | IUCLC);
	        /* set output mode with no define*/
	        newtio.c_oflag &= ~OPOST;
	        /* set input mode with non-format */
	        newtio.c_lflag &= ~(ICANON | ECHO | ECHOE | ISIG);
	        newtio.c_iflag |= IGNBRK|IGNPAR; //for 0xd,0x11,0x13

	switch (nBits) {
	case 7:
		newtio.c_cflag |= CS7;
		break;
	case 8:
		newtio.c_cflag |= CS8;
		break;
	}

	switch (nEvent) {
	case 'O':
		newtio.c_cflag |= PARENB;
	//	newtio.c_cflag |= PARODD;
	//	newtio.c_iflag |= (INPCK | ISTRIP);
		break;
	case 'E':
		newtio.c_iflag |= (INPCK | ISTRIP);
		newtio.c_cflag |= PARENB;
		newtio.c_cflag &= ~PARODD;
		break;
	case 'N':
		newtio.c_cflag &= ~PARENB;
		break;
	}

	switch (nSpeed) {
	case 2400:
		cfsetispeed(&newtio, B2400);
		cfsetospeed(&newtio, B2400);
		break;
	case 4800:
		cfsetispeed(&newtio, B4800);
		cfsetospeed(&newtio, B4800);
		break;
	case 9600:
		cfsetispeed(&newtio, B9600);
		cfsetospeed(&newtio, B9600);
		break;
	case 38400:
		cfsetispeed(&newtio, B38400);
		cfsetospeed(&newtio, B38400);
		break;
	case 115200:
		cfsetispeed(&newtio, B115200);
		cfsetospeed(&newtio, B115200);
		break;
	case 460800:
		cfsetispeed(&newtio, B460800);
		cfsetospeed(&newtio, B460800);
		break;
	default:
		cfsetispeed(&newtio, B9600);
		cfsetospeed(&newtio, B9600);
		break;
	}
	if (nStop == 1)
		newtio.c_cflag &= ~CSTOPB;
	else if (nStop == 2)
		newtio.c_cflag |= CSTOPB;
	newtio.c_cc[VTIME] = 0;
	newtio.c_cc[VMIN] = 0;
	tcflush(fd, TCIFLUSH);
	if ((tcsetattr(fd, TCSANOW, &newtio)) != 0) {
		perror("com set error");
		return -1;
	}
	return 0;
}

static int read_uart_data(char* data,  int len)
{
	 struct timeval timeout;
	 timeout.tv_sec = 0;
	 timeout.tv_usec = 500000;
	 int ret = 0;
	 memset(data,0,len);
     do {
             fd_set readfds;
             FD_ZERO(&readfds);
             FD_SET(uart_fd, &readfds);
             //wait for 2 seconds if no data come
             ret = select(FD_SETSIZE, &readfds, NULL, NULL, &timeout);
             if (ret < 0)
                     continue;
             if (FD_ISSET(uart_fd, &readfds)) {
                     ret = read( uart_fd, data, len);
             }
     } while (ret < 0 && errno == EINTR);
     return ret;
}

/*
 * 设置串口属性
 * baud: 波特率
 * dataBits:数据位数
 * parity: 校验
 * stopBits:停止位
*/
int Java_com_keruiyun_saike_UartComm_setOpt(JNIEnv* env, jobject thiz, jint baud, jint dataBits, jint parity, jint stopBits) {

		//配置串口
		char tmp;
		if(parity == 0)
			tmp = 'N';
		else if(parity == 1)
			tmp = 'O';
		else if(parity == 2)
			tmp = 'E';
		LOGD("baudrate %d databits %d parity %c stopBits %d\n",baud, dataBits, tmp, stopBits);
		int nset = set_opt(uart_fd, baud, dataBits, tmp, stopBits);
		if (nset == -1) {
			return -1;
		}


}
/*
wr: '0', 设置rs485模块为写模式
    '1', 设置rs485模块为读模式, 注意是字符串
*/
int control_rs485(char rd){
        int fd = open("/sys/class/io_control/rs485_con", O_RDWR | O_NOCTTY);
        if (fd == -1) {
                return 2;
        }
        write(fd, &rd, 1);
        close(fd);
}
/*
 * 打开串口
 * device: /dev/ttyS1 /dev/ttyS2之类的串口名字
 * */
int Java_com_keruiyun_saike_UartComm_uartInit(JNIEnv* env, jobject thiz, jstring device) {
	//打开串口
	char* dev = (char*)(*env)->GetStringUTFChars(env,device,0);
	LOGD("Uart name %s\n", dev);
	uart_fd = open(dev, O_RDWR);
	if (uart_fd == -1) {
		LOGD("###OPEN %s fail\n", dev);
		return -1;
	}
	control_rs485('1');
	(*env)->ReleaseStringUTFChars(env,device, dev);
	return 0;
}
int Java_com_keruiyun_saike_UartComm_uartDestroy(JNIEnv* env, jobject thiz) {
	close(uart_fd);
     uart_fd = -1;
}
/**
 * 把数据发送出去
 * byteBuf:数据缓冲
 * length: 要发送数据的长度
 */
#define uart_tcdrain(fd) ioctl(fd, TCSBRK, 1)
int Java_com_keruiyun_saike_UartComm_send(JNIEnv* env, jobject thiz, jintArray intBuf, jint length) {
	int i,ret = -1;
	jboolean isCopy;
	if(uart_fd == -1)
		    return -1;

	jint* arr = (int*)(*env)->GetIntArrayElements(env,intBuf,&isCopy);

	unsigned char *xwdata = malloc(length*sizeof(unsigned char));
   // LOGD("###发送：start-------------------------------");
    for(i=0; i<length; i++) {
		xwdata[i] = (unsigned char)arr[i];
        //LOGD("###发送：%d：%x ",i,xwdata[i]);
	}
   // LOGD("###发送：end-------------------------------");
	tcflush(uart_fd,   TCIOFLUSH);
	ret = write(uart_fd, xwdata, length);
	 //tcdrain函数等待所有输出都被发送。若成功为0，出错为-1
	usleep(5000);
	if( uart_tcdrain(uart_fd)==-1 )
		ret = -1;
	(*env)->ReleaseIntArrayElements(env,intBuf,(jbyte*)arr, JNI_ABORT);
	free(xwdata);
	xwdata = NULL;
	return ret;

}



/**
 *读取串口数据
 * byteBuf： 数据缓冲
 * length: 要读取的数据长度
 * return :
 */
int Java_com_keruiyun_saike_UartComm_recv(JNIEnv* env, jobject thiz, jintArray intBuf, jint length) {
		
	int i,ret = -1;
	jboolean isCopy;
	if(uart_fd == -1)
		    return -1;
	unsigned char *xrdata = malloc(length*sizeof(unsigned char));

	int* arr = (int*)(*env)->GetIntArrayElements(env,intBuf,&isCopy);

	ret = read_uart_data(xrdata, length);

	for(i=0; i<ret; i++)
		 arr[i] = xrdata[i];

	(*env)->ReleaseIntArrayElements(env,intBuf,(jbyte*)xrdata, JNI_ABORT);

	free(xrdata);
	xrdata = NULL;
	return ret;

}
/*
 * 把485设置为读模式或者写模式
 * read  1 : 设置为读模式
 * 		   0 : 设置为写模式
 * */
int Java_com_keruiyun_saike_UartComm_setRS485Read(JNIEnv* env, jobject thiz, jint read) {
	if(read == 1) {
		control_rs485('1');
	} else {
		control_rs485('0');
	}
	usleep(5000);
	return 0;
}
