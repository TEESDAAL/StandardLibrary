package base;
public interface CacheF$2p$2 extends base.CacheHandler$4sg$0{
  default Object imm$$hash$1(Object p0){
    throw new AssertionError("Uncallable method: CacheF$2p$2.imm$$hash$1"+this.getClass().getName());
  }
  default Cache1 _cache1(){
    throw new AssertionError("Uncallable method: CacheF$2p$2._cache1"+this.getClass().getName());
  }
  default Object imm$_get$1(Object p0){
    var this$= this;
    var a$= (base.Norm$o$1)p0;
    return _cache1().get(a$);
  }
}