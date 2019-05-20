package com.example.trader.Dao.Repo;

import com.alibaba.fastjson.JSON;
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
        return "/Future";
    }

    public List<Future> findAll(){
        String url = getBroker().getReadApi() + getType();
        ResponseEntity<Future[]> responseEntity = getRestTemplate().getForEntity(url, Future[].class);
        List<Future> res = Arrays.asList(responseEntity.getBody());
        System.out.println(JSON.toJSONString(res));
        return res;
    }

}
