package codeGenerate;

import java.util.List;

public class IndexData {
    public List<String> indexFields;
    public Integer indexType;//0 唯一索引，1：普通索引
    public String keyName;

    public IndexData(List<String> indexFields,Integer indexType,String keyName) {
        this.indexFields = indexFields;
        this.indexType=indexType;
        this.keyName=keyName;
    }

    public List<String> getIndexFields() {
        return indexFields;
    }

    public void setIndexFields(List<String> indexFields) {
        this.indexFields = indexFields;
    }

    public Integer getIndexType() {
        return indexType;
    }

    public void setIndexType(Integer indexType) {
        this.indexType = indexType;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("IndexData{");
        sb.append("indexFields=").append(indexFields);
        sb.append(", indexType=").append(indexType);
        sb.append(", keyName='").append(keyName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
