package life.majiang.community.mapper;

import life.majiang.community.model.Question;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface QuestionMapper {
    @Insert("INSERT INTO question (title,description,gmt_create,gmt_modified,creator,tag) VALUES (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);
    
    @Select("SELECT * FROM question LIMIT #{offset},#{size}")
	List<Question> list(@Param(value = "offset")Integer offset,@Param(value = "size") Integer size);
    
    @Select("SELECT COUNT(1) FROM question")
    Integer count();

    @Select("SELECT * FROM question WHERE creator=#{userId} LIMIT #{offset},#{size}")
	List<Question> listByUserId(@Param("userId")Integer userId, @Param(value = "offset")Integer offset,@Param(value = "size") Integer size);

    @Select("SELECT COUNT(1) FROM question WHERE creator=#{userId}")
	Integer countByUserId(@Param("userId")Integer userId);

    @Select("SELECT * FROM question WHERE id=#{id}")    
	Question getById(@Param("id") Integer id);

    @Update("UPDATE question SET title=#{title},description=#{description},tag=#{tag},gmt_modified=#{gmtModified} WHERE id=#{id}") 
	void update(Question question);
}
