package org.bluebridge.mapper;

import org.apache.ibatis.annotations.Param;
import org.bluebridge.domain.Emp;

import java.util.List;

public interface EmpMapper {

    List<Emp> selectByMultiCondition(@Param("lastName") String lastName,
                                     @Param("email") String email,
                                     @Param("gender") String gender);
}
