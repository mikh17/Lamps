package com.luxoft.lamps.storage;

import android.net.Uri;

import com.tjeannin.provigen.ProviGenBaseContract;
import com.tjeannin.provigen.annotation.Column;
import com.tjeannin.provigen.annotation.Column.Type;
import com.tjeannin.provigen.annotation.ContentUri;
import com.tjeannin.provigen.annotation.Contract;

@Contract(version = 1)
public interface ILamp extends ProviGenBaseContract  {

    @Column(Type.INTEGER)
    public static final String ID_COLUMN = "id";

    @Column(Type.TEXT)
    public static final String NAME_COLUMN = "name";

    @ContentUri
    public static final Uri CONTENT_URI = Uri.parse("content://com.luxoft.lamps/lamps");
}
