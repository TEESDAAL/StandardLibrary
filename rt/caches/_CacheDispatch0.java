package base;
interface _CacheDispatch0 extends base.CacheHandler$4sg$0{
  default Object imm$$hash$0(){
    throw new AssertionError("Uncallable method: imm$$hash$0 on "+this.getClass().getName());
  }
  default Cache0 _cache0(){
    throw new AssertionError("Uncallable method: _cache0 on "+this.getClass().getName());
  }
  default Object imm$_get$0(){
    return _cache0().get();
  }
}
