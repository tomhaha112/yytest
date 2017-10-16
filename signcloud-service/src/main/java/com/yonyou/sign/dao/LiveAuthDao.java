package com.yonyou.sign.dao;

import java.util.Map;

import com.yonyou.sign.sdk.model.LiveAuthEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveAuthDao {

	LiveAuthEntity getAuthByappId(Map<String,Object> paramMap);
}
