package com.raveleen.services;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Dialog;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Святослав on 15.01.2017.
 */
public interface DialogService {
    boolean isDialogExists(long id1, long id2);
    List<Dialog> dialogsOrderByDate(long id, int from);
    Dialog getById(long id);
    void updateDialog(Dialog dialog);
    void deleteDialogById(long id);
    void addDialog(Dialog dialog);
    Dialog getDialog(long id1, long id2);
}
