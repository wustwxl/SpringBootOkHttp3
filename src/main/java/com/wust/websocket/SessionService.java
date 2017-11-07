package com.wust.websocket;

import com.wust.entity.UserAccount;
import lombok.SneakyThrows;
import net.sf.json.JSONObject;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static com.wust.utils.OKHttp3Util.getOKHttp3;
import static com.wust.utils.OKHttp3Util.postOKHttp3;

/**
 * 处理websocket的Session
 * <p>
 * TODO：实际应用需要注意避免Map的强引用导致的内存泄漏
 */
public class SessionService {

    private static Logger logger = Logger.getLogger(SessionService.class.getName());

    /**
     * @Description: AtomicInteger是一个提供原子操作的Integer类，通过线程安全的方式操作加减。
     * count 用来记录当前在线连接数。
     */
    private static AtomicInteger count = new AtomicInteger();

    /**
     * @Description: ConcurrentHashMap在线程安全的基础上提供了更好的写并发能力，但同时降低了对读一致性的要求。
     * 用来存放每个客户端对应的Session对象。
     */
    private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    public static void newSession(Session session) {
        sessionMap.put(session.getId(), session);
        logger.info("There is a new connections, and the currCount is " + count.incrementAndGet());
    }

    public static void closeSession(Session session) {
        sessionMap.remove(session.getId());
        logger.info("A connections has been closed, and the currCount is " + count.decrementAndGet());
    }

    /**
     * 发送消息到客户端
     *
     * @param session
     * @param message
     */
    @SneakyThrows
    public static void sendMessage(Session session, String message) {

        String putMessage = handleMessage(message);

        //服务端发送信息到客户端,用日志打印在控制台
        logger.info("The Server sends messages to Client:" + putMessage);
        session.getBasicRemote().sendText(putMessage);
    }

    /**
     * 服务器将接收到的信息进行处理返回MessageVO结果到客户端
     *
     * @param message
     */
    public static String handleMessage(String message) {

        logger.info("服务器将接收到的信息进行处理。");
        String mess = null;

        if ("Control".equals(message)) {

            logger.info("服务器接收到 [控制] 指令。");
            mess = "Control Success";

        } else if ("Query".equals(message)) {

            logger.info("服务器接收到 [查询] 指令。");

            //本项目get请求
            String url = "http://localhost:8080/useraccount/num";
            mess = getOKHttp3(url);

        }else if ("User".equals(message)) {

            logger.info("服务器接收到 [User] 指令。");

            //本项目post请求
            String url = "http://localhost:8080/useraccount/test";

            UserAccount userAccount = new UserAccount();
            userAccount.setTel("12345678965");
            userAccount.setPass("123");

            JSONObject json = JSONObject.fromObject(userAccount);
            String str = json.toString();

            mess = postOKHttp3(url, str);

        } else {

            logger.info("服务器接收到 [其余] 指令。");
            mess = "Other Message";

        }

        return mess;
    }

}
