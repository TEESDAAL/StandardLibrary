package base;
public interface CacheMemo$lk$3 extends base.CacheHandler$4sg$0{
  default Object imm$$hash$2(Object p0, Object p1){
    throw new AssertionError("Uncallable method: CacheMemo$lk$3.imm$$hash$2"+this.getClass().getName());
  }
  default Cache2 _cache2(){
    throw new AssertionError("Uncallable method: CacheMemo$lk$3._cache2"+this.getClass().getName());
  }
  default Object imm$_get$2(Object p0, Object p1){
    var this$= this;
    var a$= (base.Norm$o$1)p0;
    var b$= (base.Norm$o$1)p1;
    return _cache2().get(a$,b$);
  }
}