package Tools;

/**
 * 
 * @ClassName: MsgTypes
 * @Description: TODO 规定通信协议
 * @author TwoDaysZzz
 * @date 2021年5月18日
 *
 */
public interface MsgTypes {
	String MESSAGE_LOGIN_REQUEST = "1";
	String MESSAGE_LOGIN_SUCCESS = "2";
	String MESSAGE_LOGIN_FAIL = "3";
	String MESSAGE_COMMON_ = "4";
	String MESSAGE_GET_ONLINE_FRI = "5";
	String MESSAGE_RET_ONLINE_FRI = "6";
	String MESSAGE_CLIENT_EXIT = "7";// 为了正常退出
	String MESSAGE_TO_ALL_MSG = "8";
	String MESSAGE_FILE_MSG = "9";
	String MESSAGE_REGISTER_ = "10";
	String MESSAGE_REGISTER_SUCCESS = "11";
	String MESSAGE_REGISTER_FAIL = "12";
	String MESSAGE_PRIVATE_CHAT_REQUEST = "13";
	String MESSAGE_QUN_CHAT = "14";
	String MESSAGE_FRIENG_LIST = "15";
	String MESSAGE_LOGIN_OUT = "16";
	String MESSAGE_LOGIN_RESPONSE = "17";
	String MESSAGE_PRIVATE_CHAT_RESPONSE = "18";

}
