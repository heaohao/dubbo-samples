package org.apache.dubbo.springboot.demo.consumer;

import com.alibaba.dubbo.common.Constants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.config.configcenter.ConfigChangeType;
import org.apache.dubbo.common.config.configcenter.ConfigChangedEvent;
import org.apache.dubbo.common.config.configcenter.ConfigurationListener;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.common.utils.Holder;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.router.RouterSnapshotNode;
import org.apache.dubbo.rpc.cluster.router.state.AbstractStateRouter;
import org.apache.dubbo.rpc.cluster.router.state.BitList;

public class StickFirstStateRouter<T> extends AbstractStateRouter<T> implements ConfigurationListener {
    public StickFirstStateRouter(URL url) {
        super(url);
    }

    public static final String NAME = "STICK_FIRST_ROUTER";
    private volatile BitList<Invoker<T>> firstInvokers;

    @Override
    protected BitList<Invoker<T>> doRoute(BitList<Invoker<T>> invokers, URL url, Invocation invocation, boolean needToPrintMessage, Holder<RouterSnapshotNode<T>> routerSnapshotNodeHolder, Holder<String> messageHolder) throws RpcException {
        if (CollectionUtils.isEmpty(invokers)) {
            if (needToPrintMessage) {
                messageHolder.set("Directly Return. Reason: Invokers from previous router is empty.");
            }
            return invokers;
        }
        BitList<Invoker<T>> res = new BitList<>(BitList.emptyList());
        for (Invoker<T> invoker : invokers) {
            String providerTag = invoker.getUrl().getParameter(Constants.TAG_KEY);

            if ("guhao".equals(providerTag)){
                res.add(invoker);
            }
        }
        if (res.isEmpty()){
            return invokers;
        }else {
            return res;
        }
//        BitList<Invoker<T>> copy = invokers.clone();
//        if (CollectionUtils.isEmpty(copy)) {
//            this.firstInvokers = new BitList<>(BitList.emptyList());
//            this.firstInvokers.add(copy.get(0));
//        } else {
//            this.firstInvokers = copy.and(invokers);
//            if(CollectionUtils.isEmpty(this.firstInvokers)){
//                this.firstInvokers.add(copy.get(0));
//            }
//        }
//        return this.firstInvokers;
    }

    @Override
    public void process(ConfigChangedEvent event) {
        // Reset
        if (event.getChangeType().equals(ConfigChangeType.DELETED)) {
            this.firstInvokers = null;
        }
    }

    @Override
    public void stop() {
        super.stop();
    }
}