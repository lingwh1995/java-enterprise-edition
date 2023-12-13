# 1.RowMapper主要实现类
    BeanPropertyRowMapper   用于把查询到的数据封装到实体中或者List中
# 2.RowMapper的高级用法，自己写一个类实现RowMapper这个接口，重写mapRow()方法后可以使用ResultSet取到的值灵活的把数据封装到自己需要的数据结构中
    
    @FunctionalInterface
    public interface RowMapper<T> {
        @Nullable
        T mapRow(ResultSet rs, int rowNum) throws SQLException;
    }