package com.ccloudapp.fit403.util;

import io.reactivex.disposables.Disposable;
import rx.Subscription;

/**
 * Create by Amit Kumar on 8/6/17
 * Email : mr.doc10jl96@gmail.com
 * Company : Dot Wave Ltd.
 */

public class RxUtil {

    public static void unsubscribe(Subscription subscription){
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
