package com.server;

import com.common.JT808Const;
import com.service.TerminalMsgProcessService;
import com.service.codec.MsgDecoder;
import com.vo.PackageData;
import com.vo.PackageData.MsgBody;
import com.vo.req.LocationMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * ClassName: ServerHandler 
 * @Description: 业务处理handler
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private MsgDecoder msgDecoder;
    private TerminalMsgProcessService msgProcessService;
    
    public ServerHandler() {
        this.msgDecoder = ServerApplication.appCtx.getBean(MsgDecoder.class);
        this.msgProcessService = ServerApplication.appCtx.getBean(TerminalMsgProcessService.class);
    }
    
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
		try {
			ByteBuf buf = (ByteBuf) msg;
			if (buf.readableBytes() <= 0) {
				return;
			}
			byte[] bs = new byte[buf.readableBytes()];
			buf.readBytes(bs);
			// 字节数据转换为针对于808消息结构的业务对象
			PackageData pkg = this.msgDecoder.bytes2PackageData(bs);
			// 引用channel,以便回送数据给终端
			pkg.setChannel(ctx.channel());
			this.processPackageData(pkg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		SessionManager.getInstance().removeSessionByChannelId(ctx.channel().id().asLongText());
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		
	}
	
	//业务处理逻辑
	private void processPackageData(PackageData packageData) throws Exception {
		MsgBody body = packageData.getMsgBody();
		int bodyId = body.getBodyId();
		//任务类业务处理，这里是接收终端主动上报的信息，包括登录、上报的位置信息、上报的事件等等
		if (bodyId == JT808Const.TASK_BODY_ID_LOGIN) {
			this.msgProcessService.processLoginMsg(packageData);
		} else if (bodyId == JT808Const.TASK_BODY_ID_GPS) {
			LocationMsg msg = this.msgDecoder.toLocationMsg(packageData);
			this.msgProcessService.processLocationMsg(msg);
		}
	}
}
