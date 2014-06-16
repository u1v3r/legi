package sk.rdy.legi.db.dao;

import android.content.Context;
import sk.rdy.legi.db.helper.meta.SubscriptionMeta;
import sk.rdy.legi.db.model.Subscription;

/**
 * Created by rdy on 16.7.2013.
 */
public class SubscriptionDao extends AbstractDao<Subscription,SubscriptionMeta> {

    /**
     * @param context
     * @param modelClazz
     * @param tableMetaClazz
     */
    public SubscriptionDao(Context context, Class<Subscription> modelClazz, Class<SubscriptionMeta> tableMetaClazz) {
        super(context, modelClazz, tableMetaClazz);
    }
}
