package sk.rdy.legi.db.dao;

import android.content.Context;
import sk.rdy.legi.db.helper.meta.FeedItemMetaTable;
import sk.rdy.legi.db.model.FeedItem;

/**
 * Created by rdy on 22.7.2013.
 */
public class FeedItemDao extends AbstractDao<FeedItem,FeedItemMetaTable> {

    /**
     * @param context
     * @param modelClazz
     * @param tableMetaClazz
     */
    public FeedItemDao(Context context, Class<FeedItem> modelClazz, Class<FeedItemMetaTable> tableMetaClazz) {
        super(context, modelClazz, tableMetaClazz);
    }
}
