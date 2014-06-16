package sk.rdy.legi.db.dao;

import android.content.Context;
import sk.rdy.legi.db.helper.meta.FolderMetaTable;
import sk.rdy.legi.db.model.Folder;

/**
 * Created by rdy on 12.7.2013.
 */
public class FolderDao extends AbstractDao<Folder,FolderMetaTable> {

    public FolderDao(Context context, Class<Folder> modelClazz, Class<FolderMetaTable> metaClazz) {
        super(context, modelClazz, metaClazz);
    }
}
