package fi.m1kah.audit;

/*
Copyright (c) 2013 Mika Hämäläinen

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

import fi.m1kah.auth.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

@Aspect
@Order(1)
public class AuditLogAspect {
    private final Logger audit = LoggerFactory.getLogger("audit");

    @Before(value = "@annotation(auditLog)", argNames = "joinPoint, auditLog")
    public void before(JoinPoint joinPoint, AuditLog auditLog) {
        final User user = User.getCurrent();
        final String args = getArgs(joinPoint.getArgs());
        audit.info("user: {}, serviceId: {}, serviceName: {}, args: {}",
            user, auditLog.serviceId(), auditLog.serviceName(), args);
    }

    @AfterReturning(value = "@annotation(auditLog)", argNames = "joinPoint, auditLog, returnValue", returning = "returnValue")
    public void after(JoinPoint joinPoint, AuditLog auditLog, Object returnValue) {
        final User user = User.getCurrent();
        final String args = getArgs(joinPoint.getArgs());
        audit.info("user: {}, serviceId: {}, serviceName: {}, args: {}, returning: {}",
                user, auditLog.serviceId(), auditLog.serviceName(), args, returnValue);
    }

    @AfterThrowing(value = "@annotation(auditLog)", argNames = "joinPoint, auditLog, throwable", throwing = "throwable")
    public void afterThrow(JoinPoint joinPoint, AuditLog auditLog, Throwable throwable) {
        final User user = User.getCurrent();
        final String args = getArgs(joinPoint.getArgs());
        audit.info("user: {}, serviceId: {}, serviceName: {}, args: {}, throwing: {}",
                user, auditLog.serviceId(), auditLog.serviceName(), args, throwable);
    }

    private String getArgs(Object... args) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < args.length; ++i) {
            sb.append(args[i]);
            if (i + 1 < args.length) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
