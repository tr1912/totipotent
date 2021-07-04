package com.wx.lab.view.exception;

public enum BusinessExceptionEnum {

    SUCCESS(0, "调用成功"),
    ERROR_CODE(-1, "操作失败,请重新尝试"),
    DUPLICATE_SUBMIT(8008001, "请勿重复提交"),
    ERROR(4030001, "用户id参数错误"),
    ERROR_INIT(55197101, "对象初始化失败"),
    ERROR_ORIGINAL_PRODUCT_CODE(55199101, "无效的原商品编码"),
    ERROR_ORIGINAL_CODE(55199102, "无效的商品编码"),
    ERROR_ORIGINAL_MANUFACTURER_NAME(55199103, "无效的生产厂家名称"),
    ERROR_APPROVAL_NO(55199104, "批准文号不符合规范,请重新录入"),
    ERROR_IO(55196101, "通讯超时或IO异常"),
    ERROR_NULL(55195101, "类不存在"),
    ERROR_DATA_NULL(55181101, "数据不存在"),
    ERROR_ARITHMETIC(55194101, "运算异常"),
    ERROR_INDEXOUT(55193101, "数组越界"),
    ERROR_CONVERT(55192101, "类型转换错误"),
    ERROR_PARAM(55191101, "方法的参数错误"),
    ERROR_SECURITY(55190101, "安全异常"),
    ERROR_SQL(55189101, "操作数据库异常"),
    ERROR_INNER(55188101, "程序内部错误，操作失败"),
    ERROR_DATABASE(55198101, "数据库操作失败"),
    ERROR_ADD_SPU_SINGLE(55198102, "批准文号已存在，不可重复新增，spu编码为:%s"),
    ERROR_DUPLICATE_SPU_SINGLE(55198103, "批准文号已存在，不可重复新增，spu编码为:%s"),
    ERROR_DUPLICATE_APPROVAL_NO(55198104, "批准文号已存在"),

    ERROR_PAEAM(55191101, "方法的参数错误"),
    ERROR_MQ(55199101, "mq消息发送失败"),
    ERROR_ALREADY_EXISTS(55191102, "账号已存在"),
    NO_DISABLE(55191104, "该节点下有启用的子节点,不可停用！"),
    MAX_LEVEL(55191107, "已到最大分类级别！"),
    NOT_EMPTY(55191108, "分类名称不可为空"),
    DISABLE_NOT_ADD(55191109, "停用节点下不可新增节点"),
    NO_REPEAT(55191105, "已存在，请勿重复添加"),
    MAX_CHILDREN(55191106, "该节点下子级数量已达到最大限制"),
    UNIQUE_GROUP(55191105, "只能有一个集团机构"),
    PLEASE_MODIFY(55191201, "请修改后再保存"),
    APPLY_REASON_EMPTY(55191202, "请填写申请原因"),
    USING(55191203, "该字典正在被使用，不可停用"),
    APPLY_REASON_TOO_LONG(55191204, "申请原因不能超过100字符"),
    ANALYSIS_FAIL(55191106, "解析失败，请确认模板和数据正确"),
    ERROR_IS_USERD(55191106, "该角色仍有用户在使用"),
    ERROR_RPC(55198101, "RPC调用异常"),

    ACCOUNT_IS_STOP_ERROR(4030002,"账号已停用，如有疑问请联系系统管理员"),
    ACCOUNT_IS_EXCEPTION_ERROR(4030003,"账号不存在,请联系管理员"),
    ACCOUNT_PARAMS_ERROR(4030004,"用户参数有误"),
    PASSWORD_ERROR(4030005,"账号密码错误"),
    CODE_ERROR(4030006,"验证码错误"),
    CHANGE_PWD_PARAMS_ERROR(4030007,"用户修改密码参数有误"),
    PWD_IS_DIFFERENCE_ERROR(4030008,"新密码和确认密码不同"),
    ROLE_INFO_ERROR(4030009,"获取用户角色信息失败"),
    DEPARTMENT_INFO_ERROR(4030010,"获取用户部门信息失败"),
    INSTITUTIONS_INFO_ERROR(4030011,"获取用户机构信息失败"),
    JOBS_INFO_ERROR(4030012,"获取用户岗位信息失败"),
    ERROR_FILE_EMPTY(4030013,"上传文件为空"),
    ERROR_FORMAT_EXCEL(4030014,"excel文件内容有误，请修改后上传"),
    ERROR_NOT_EXCEL(4030015,"请上传excel文件"),
    ERROR_FILE_SIZE(4030016,"上传文件过大,请重新上传"),
    COPY_NOT_CHANGE(4030017,"新增失败,请填写信息后再提交"),
    FILE_IS_NULL_ERROR(4030018,"上传文件为空"),
    FILE_UOLOAD_ERROR(4030019,"上传文件失败,请重新上传"),
    ERROR_FILE_SIZE_500(4030020,"上传文件不能大于500Mb"),
    FILE_ANALYSIS_ERROR(4030021,"文件解析失败,请重新上传"),
    ERROR_FILE_SIZE_200(4030022,"上传文件不能大于200Mb"),
    //商品相关

    CHECK_PRODUCT_ERROR(2030001,"参数传递有误,请联系管理员"),
    GENERATE_APPLY_CODE_ERROR(2030002,"单据编号生成失败"),
    BUILD_TMP_SPU_ERROR(2030003,"修改商品失败,生成SPU有误"),
    BUILD_TMP_SKU_ERROR(2030004,"修改商品失败,生成SKU有误"),
    BUILD_TMP_SAU_ERROR(2030005,"修改商品失败,生成SAU有误"),
    BUILD_TMP_EXTEND_ERROR(2030006,"修改商品失败,生成扩展属性有误"),
    SAVE_PRODUCT_DB_ERROR(2030007,"保存商品信息异常,请联系管理员"),
    SAVE_PRODUCT_EXTEND_ERROR(2030008,"保存商品扩展属性异常,请联系管理员"),
    SAVE_PRODUCT_PRE_OPARATE_ERROR(2030009,"预首营失败,请检查当前预首营状态"),
    APPLY_CODE_ERROR(2030010,"无单据编号,请联系管理员"),
    NOT_CHANGE_ERROR(2030011,"请先修改后再提交"),
    PRE_OPARATEING_ERROR(2030012, "已提交预首营申请"),
    SMALL_PACKAGE_NULL_ERROR(2030013, "小包装条码不能为空"),
    ADD_PRODUCT_ERROR(2030014,"新增商品异常,请联系管理员"),
    ADD_TMP_SPU_ERROR(2030015,"新增商品SPU失败,请联系管理员"),
    ADD_TMP_SKU_ERROR(2030016,"新增商品SKU失败,请联系管理员"),
    ADD_TMP_SAU_ERROR(2030017,"新增商品SAU失败,请联系管理员"),
    SMALL_PACKAGE_MATCH_ERROR(2030018, "小包装条码不规范,请重新输入"),
    PICTURE_UPLOAD_ERROR(2030019, "图片下载失败"),
    BUILD_TMP_SPU_SKU_ZERO_ERROR(2030020,"修改商品失败, sku数量为0才能修改"),

    //拍摄图片相关
    SHOOT_PICTURE_NOT_FOUND(3030011,"未上传原图且未绑定图片版本"),
    SHOOT_PICTURE_NOT_FULL_BOUND(3030012,"有商品未绑定图片"),
    SHOOT_PICTURE_ERROR_FILE_NAME_FORMAT(3030013,"不满足图片名称规则：【商品编码-图片序号】.png|jpg|jpeg|bmp"),
    SHOOT_PICTURE_REPEAT_FILE_NAME(3030014,"图片名称重复，请修改后保存"),
    SHOOT_PICTURE_SPLIT_DETAIL_TASK_ERROR(3030015,"分解精修图任务异常"),
    CHOOSE_SHOOT_PRODUCT(3030016,"请选择待拍摄商品"),
    ONE_SHOOT_PRODUCT(3030017,"一个商品只能选择一个机构"),
    SHOOT_PRODUCT_ERROR(3030018,"有异常商品无法提交"),
    SHOOT_TASK_RECEIVED(3030019,"任务已被领取"),
    SHOOT_TASK_AUDITED(3030020,"任务已审核"),
    TASK_DETAIL_NOT_FOUND(3030021,"未发现拍摄任务商品明细"),
    PICTURE_ANNEX_NOT_FOUND(3030021,"未上传商品图片附件"),
    PICTURE_ANNEX_NOT_NEED_PARSE(3030022,"未有待解析的附件，附件在解析中或解析完毕"),
    ERROR_FILE_NAME_FORMAT(3030023,"不满足图片名称规则：【商品编码-图片序号】.png|jpg|jpeg|bmp"),
    REPEAT_FILE_NAME(3030024,"图片名称重复"),
    DIFFERENT(3030025,"精修图和原图数量不一致，请重新上传"),
    PICTURE_NAME_ERROR(3030026,"上传图片无法识别"),
    UNKNOWN_EXCEPTION(3030027,"未知解析异常"),
    NOT_FULL_BOUND(3030028,"有商品未绑定图片版本"),
    ZIP_NULL(3030029,"上传文件为空，请重新上传");

    private Integer code;

    private String desc;

    private BusinessExceptionEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
