package base;

public interface _FloatNext$0 extends base.Sealed$0 {
    default Object imm$succ$1(Object p0) {
        return Float$0Instance.instance(
                Math.nextUp(Float$0Instance.unwrap(p0))
        );
    }

    default Object imm$pred$1(Object p0) {
        return Float$0Instance.instance(
                Math.nextDown(Float$0Instance.unwrap(p0))
        );
    }
}
