package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user (name,account_id,token,gmt_create,gmt_modified,avatar_url) VALUES (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("SELECT * FROM user WHERE token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("SELECT * FROM user WHERE id = #{id}")
	User finById(@Param("id")Integer id);

    @Select("SELECT * FROM user WHERE account_id = #{accountId}")
	User findByAccountId(@Param("accountId") String accountId);

    @Update("UPDATE user SET name=#{name},token=#{token},avatar_url=#{avatarUrl},gmt_modified=#{gmtModified} WHERE account_id=#{accountId}")
	void update(User user);
}
