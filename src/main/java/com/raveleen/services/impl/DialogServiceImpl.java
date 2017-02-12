package com.raveleen.services.impl;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Dialog;
import com.raveleen.repositories.DialogRepository;
import com.raveleen.services.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Святослав on 03.02.2017.
 */
public class DialogServiceImpl implements DialogService {
    @Autowired
    private DialogRepository dialogRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean isDialogExists(long id1, long id2) {
        return dialogRepository.isDialogExists(id1, id2);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dialog> dialogsOrderByDate(long id, int from) {
        int a;
        if (from == 0) {
            a = 0;
        } else {
            a = from / 10;
        }
        Pageable temp = new PageRequest(a, 10);
        return dialogRepository.getDialogs(id, temp);
    }

    @Override
    @Transactional(readOnly = true)
    public Dialog getById(long id) {
        return dialogRepository.findOne(id);
    }

    @Override
    @Transactional
    public void updateDialog(Dialog dialog) {
        dialogRepository.saveAndFlush(dialog);
    }

    @Override
    @Transactional
    public void deleteDialogById(long id) {
        dialogRepository.delete(id);
    }

    @Override
    @Transactional
    public void addDialog(Dialog dialog) {
        dialogRepository.saveAndFlush(dialog);
    }

    @Override
    @Transactional(readOnly = true)
    public Dialog getDialog(long id1, long id2) {
        return dialogRepository.getDialog(id1, id2);
    }
}
