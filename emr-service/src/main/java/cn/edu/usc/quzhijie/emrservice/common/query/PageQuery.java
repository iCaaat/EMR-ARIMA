package cn.edu.usc.quzhijie.emrservice.common.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageQuery {
    private Integer pageNum = 1;
    private Integer pageSize = 10;

    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
