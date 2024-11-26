package com.xinshiyun.ddr;

import com.alibaba.dubbo.common.Constants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.config.configcenter.ConfigChangedEvent;
import org.apache.dubbo.common.config.configcenter.ConfigurationListener;
import org.apache.dubbo.common.utils.Holder;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.router.RouterSnapshotNode;
import org.apache.dubbo.rpc.cluster.router.state.AbstractStateRouter;
import org.apache.dubbo.rpc.cluster.router.state.BitList;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

public class LocalTagStateRouter<T> extends AbstractStateRouter<T> implements ConfigurationListener {

    public static final String NAME = "LOCAL_TAG_ROUTER";

    public LocalTagStateRouter(URL url) {
        super(url);
    }

    @Override
    protected BitList<Invoker<T>> doRoute(BitList<Invoker<T>> invokers,
                                          URL url,
                                          Invocation invocation,
                                          boolean needToPrintMessage,
                                          Holder<RouterSnapshotNode<T>> routerSnapshotNodeHolder,
                                          Holder<String> messageHolder) throws RpcException {
        if (tag == null) {
            ApplicationContext context = ApplicationContextProvider.getApplicationContext();
            tag = context.getEnvironment().getProperty("u-name");
            System.out.println("LocalTagStateRouter :" + tag);
        }
        if (StringUtils.hasText(tag)) {
            invocation.setAttachment(Constants.TAG_KEY, tag);
        }
        return invokers;
    }

    @Override
    public void process(ConfigChangedEvent event) {

    }

    @Override
    public void stop() {
        super.stop();
    }


    private String tag;

}