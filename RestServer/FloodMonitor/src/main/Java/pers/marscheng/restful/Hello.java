package pers.marscheng.restful;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * restful测试类
 *
 * @author marscheng
 * @create 2017-07-26 下午3:19
 *
 *
 * @PATH对应的是我们要配置的restful api的子路劲，比如前面我们配置的是/api/*,则用户访问该API的路径就是https//:ip:port/api/hello
 * @GET @POST对应的是用户请求资源用的HTTP方法
 * @Produces表示返回的数据类型，如MediaType.TEXT_PLAIN对应返回文本类型
 */
@Path("hello")
public class Hello {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(){
        return "Hello,I am text!";
    }


    @POST
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello,I am xml!" + "</hello>";
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html> " + "<title>" + "Hello Jersey" + "</title>"
                + "<body><h1>" + "Hello,I am html!" + "</body></h1>" + "</html> ";
    }


}