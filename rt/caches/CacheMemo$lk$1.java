package base;
public interface CacheMemo$lk$1 extends base.CacheHandler$4sg$0{
  default Object imm$$hash$0(){
    throw new AssertionError("Uncallable method: CacheMemo$lk$1.imm$$hash$0"+this.getClass().getName());
  }
  default Cache0 _cache0(){
    throw new AssertionError("Uncallable method: CacheMemo$lk$1._cache0"+this.getClass().getName());
  }
  default Object imm$_get$0(){
    return _cache0().get();
  }
}