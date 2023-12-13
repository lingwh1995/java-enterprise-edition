package com.dragonsoft.ioc.autoinject.dao;

import com.dragonsoft.ioc.autoinject.dbutils.DBUtils;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author ronin
 */
@Repository
public class BookDao {

    //@Resource(name="dbutils")
    @Inject
    private DBUtils dbUtils;

    public void say(){
        dbUtils.save();
    }

    private String label = "被@Repository标注的Dao";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "label='" + label + '\'' +
                '}';
    }
}
