package com.ccloudapp.fit403.ui.base;

/**
 * Create by Amit Kumar on 18/6/17
 * Email : mr.doc10jl96@gmail.com
 * Company : Dot Wave Ltd.
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V>  {

    private V view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public V getView() {
        return view;
    }

    public boolean isViewAttached() {
        return this.view != null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new ViewNotAttachedException();
    }

    public static class ViewNotAttachedException extends RuntimeException {
        public ViewNotAttachedException() {
            super("Call Presenter.attachView(BaseView)before" +
                    " requesting data to the Presenter");
        }
    }
}
