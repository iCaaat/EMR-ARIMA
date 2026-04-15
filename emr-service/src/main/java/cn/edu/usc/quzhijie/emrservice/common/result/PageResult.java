package cn.edu.usc.quzhijie.emrservice.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private Long total;

    /** 当前页 */
    private Integer page;

    /** 每页大小 */
    private Integer size;

    /** 数据列表 */
    private List<T> records;

    public static <T> PageResult<T> empty() {
        return new PageResult<>(0L, 0, 0, List.of());
    }
}
