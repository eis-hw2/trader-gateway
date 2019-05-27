package com.example.trader.Dao.Repo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.trader.Domain.Entity.Future;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("FutureDao")
@Scope("prototype")
public class FutureDao extends DynamicDao<String ,Future> {

    @Override
    public String getType() {
        return "futures";
    }

    @Override
    public Class<Future> getValueClass() {
        return Future.class;
    }

    @Override
    public Class<Future[]> getValueArrayClass() {
        return Future[].class;
    }

}
