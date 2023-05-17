//package com.xatu.gateway.config;
//
//
////import cn.dev33.satoken.exception.DisableLoginException;
//import cn.dev33.satoken.exception.NotLoginException;
////import cn.dev33.satoken.exception.NotPermissionException;
////import cn.dev33.satoken.exception.NotRoleException;
////import com.frontop.meta.constant.ResponseCodeConstant;
////import com.frontop.meta.constant.ResponseMessageConstant;
//import com.xatu.common.constant.CodeConstants;
//import com.xatu.common.domain.Result;
//import org.springframework.web.bind.annotation.ExceptionHandler;
////import org.springframework.web.bind.annotation.ResponseBody;
//
//
///**
// * @author Wang Lei
// */
//public class GlobalException {
////    /**
////     * 全局异常拦截（拦截项目中的所有异常）
////     * @param e 异常
////     * @return 返回Result类
////     */
////    @ResponseBody
////    @ExceptionHandler
////    public Result<Object> handlerException(Exception e) {
////
////        // 打印堆栈，以供调试
////        System.out.println("全局异常---------------");
////        e.printStackTrace();
////
////        // 不同异常返回不同状态码
////        Result<Object> re = null;
////        // 如果是未登录异常
////        if (e instanceof NotLoginException) {
////            NotLoginException ee = (NotLoginException) e;
////            re =  new ResultJsonUtil().customized(
////                    ResponseCodeConstant.OAUTH_TOKEN_FAILURE,
////                    ResponseMessageConstant.OAUTH_TOKEN_MISSING,
////                    null
////            );
////        }
////        else if(e instanceof NotRoleException) {		// 如果是角色异常
////            NotRoleException ee = (NotRoleException) e;
////            re =  new ResultJsonUtil().customized(
////                    ResponseCodeConstant.OAUTH_TOKEN_DENIED,
////                    "无此角色：" + ee.getRole(),
////                    null
////            );
////        }
////        else if(e instanceof NotPermissionException) {	// 如果是权限异常
////            NotPermissionException ee = (NotPermissionException) e;
////            re =  new ResultJsonUtil().customized(
////                    ResponseCodeConstant.OAUTH_TOKEN_DENIED,
////                    "无此角色：" + ee.getCode(),
////                    null
////            );
////        }
////        else if(e instanceof DisableLoginException) {	// 如果是被封禁异常
////            DisableLoginException ee = (DisableLoginException) e;
////            re =  new ResultJsonUtil().customized(
////                    ResponseCodeConstant.USER_LOCK,
////                    "账号被封禁：" + ee.getDisableTime() + "秒后解封",
////                    null
////            );
////        }
////        else {	// 普通异常, 输出：500 + 异常信息
////            re =  new ResultJsonUtil().fail(e.getMessage());
////        }
////
////        // 返回给前端
////        return re;
////    }
//
//    /**
//     * 全局异常拦截（拦截项目中的NotLoginException异常）
//     */
//    @ExceptionHandler(NotLoginException.class)
//    public Result handlerNotLoginException(NotLoginException nle) throws Exception {
//
//        // 打印堆栈，以供调试
//        nle.printStackTrace();
//
//        // 判断场景值，定制化异常信息
//        String message = "";
//        if(nle.getType().equals(NotLoginException.NOT_TOKEN)) {
//            message = "未提供token";
//        }
//        else if(nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
//            message = "token无效";
//        }
//        else if(nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
//            message = "token已过期";
//        }
//        else if(nle.getType().equals(NotLoginException.BE_REPLACED)) {
//            message = "token已被顶下线";
//        }
//        else if(nle.getType().equals(NotLoginException.KICK_OUT)) {
//            message = "token已被踢下线";
//        }
//        else {
//            message = "当前会话未登录";
//        }
//
//        // 返回给前端
//        return Result.error(CodeConstants.SATOKEN_ERROR, message);
//    }
//}
