package base;

import java.util.*;
import java.util.stream.IntStream;

import static base.Util.*;
import static base.Util.bool;
import static base.Util.callMF$2;
import static base.Util.err;
import static base.Util.isFalse;
import static base.Util.isTrue;
import static base.Util.optEmpty;
import static base.Util.optSome;

public interface ESparseLists$5jk$0 {
    default Object imm$backedWithArray$1(Object p0){
        long capacity = Nat$c$0Instance.unwrap(p0);
        return null;
    }
    default Object imm$backedWithMap$1(Object p0){
        long capacity = Nat$c$0Instance.unwrap(p0);
        return null;
    }
    default Object imm$backedWithSparseSegmentTree$1(Object p0){
        long capacity = Nat$c$0Instance.unwrap(p0);
        return null;
    }

    ESparseLists$5jk$0 instance= new ESparseLists$5jk$0(){};
}


class SparseArray implements ESparseList$2rs$1 {
    int capacity;
    int numHoles;
    Object[] inner;

    SparseArray(SparseArray arr) {
        this.capacity = arr.capacity;
        this.numHoles = arr.numHoles;
        this.inner = new Object[arr.inner.length];
        System.arraycopy(arr.inner, 0, this.inner, 0, arr.inner.length);
    }
    public boolean isEmpty() {
        return capacity == numHoles;
    }
    void swap(int i, int j) {
        Object temp = inner[i];
        inner[i] = inner[j];
        inner[j] = temp;
    }
    void remove(int i) {
        if (inner[i] == null) { continue }
        inner[i] = null;
        numHoles++;
    }
    @Override public Object mut$all$1(Object p0) {
        if (this.isEmpty()) { return bool(true); }
        boolean allMatch = true;
        for (Object e : inner) {
            if (e == null) { continue; }
            if (isTrue(callMF$2(p0, e))) {
                allMatch = false;
                break;
            }
        }
        return bool(allMatch);
    }
    @Override public Object mut$any$1(Object p0) {
        if (this.isEmpty()) {
            return bool(false);
        }
        boolean anyMatch = false;
        for (Object e : inner) {
            if (e == null) { continue; }
            if (isFalse(callMF$2(p0, e))) {
                anyMatch = true;
                break;
            }
        }
        return bool(anyMatch);
    }
    @Override public Object mut$none$1(Object p0) {
        if (this.isEmpty()) {
            return bool(true);
        }
        boolean noneMatch = true;
        for (Object e : inner) {
            if (e == null) { continue; }
            if (isFalse(callMF$2(p0, e))) {
                noneMatch = false;
                break;
            }
        }
        return bool(noneMatch);
    }

    @Override public Object mut$firstIndexWhere$1(Object p0) {
        if (this.isEmpty()) { return optEmpty(); }
        for (int i=0; i<inner.length; i++) {
            Object elem = inner[i];
            if (elem == null) { continue; }
            if (isTrue(callMF$2(p0, elem))) {
                return optSome(Nat$c$0Instance.instance(i));
            }
        }
        return optEmpty();
    }
    @Override public Object mut$lastIndexWhere$1(Object p0) {
        if (this.isEmpty()) { return optEmpty(); }
        for (int i=inner.length-1; i>=0; i--) {
            Object e = inner[i];
            if (e == null) { continue; }
            if (isTrue(callMF$2(p0, e))) {
                return optSome(Nat$c$0Instance.instance(i));
            }
        }
        return optEmpty();
    }
    @Override public Object mut$indicesWhere$1(Object p0) {
        if (this.isEmpty()) { return Flow$o$1Instance.of(); }
        return Flow$o$1Instance.of(IntStream.range(0, inner.length)
          .filter(i -> {
              Object e = inner[i];
              return e != null && isTrue(callMF$2(p0, e));
          })
          .mapToObj(Nat$c$0Instance::instance));
    }
    @Override public Object mut$set$2(Object p0, Object p1) {
        int index = idx(p0, ".set");
        if (inner[index] == null) {
            numHoles += 1;
        }
        inner[index] = p1;
        return this;
    }

    private int idx(Object p0, String method) {
        long index = Nat$c$0Instance.unwrap(p0);
        check(
          Long.compareUnsigned(index, inner.length) < 0,
          "ESparseList"+method+": Index "+Long.toUnsignedString(index)+" is out of bounds for a ESparseList with capacity"+capacity
        );
        return (int) index;
    }

    @Override public Object mut$clear$0() {
      if (this.isEmpty()) { return this; }
      this.numHoles = 0;
      Arrays.fill(inner, null);
      return this;
    }
    @Override public Object mut$removeIf$1(Object p0) {
        if (this.isEmpty()) { return this; }
        for (int i=0; i<inner.length; i++) {
            remove(i);
        }
        return this;
    }
    @Override public Object mut$remove$1(Object p0) {
        int index = idx(p0, ".remove");
        if (inner[index] != null) {
            this.numHoles -= 1;
            this.inner[index] = null;
        }
        return this;
    }
    @Override public Object mut$removeAll$1(Object p0) {
        if (this.isEmpty()) { return this; }
        List<Integer> indicies = EList$1k$1Instance.unwrap(p0);
        Set<Integer> seen = new HashSet<>(indicies);
        for (int index : indicies) {
            if (!seen.add(index)) {
                throw err("EList.removeAll: Index "+indicies.get(i)+" is a duplicated index: "+indicies);
            }
        }
        for (int index : indicies) {
            remove(index);
        }
        return this;
    }
    @Override public Object mut$reverse$0() {
        if (this.isEmpty()) { return this; }
        for (int i=0; i < inner.length / 2; i++) {
            swap(i, inner.length - 1 - i);
        }
        return this;
    }

    @Override public Object mut$mapInPlace$1(Object p0) {
        if (this.isEmpty()) { return this; }
        for (int i=0; i<inner.length; i++) {
            if (inner[i] == null) { continue; }
            inner[i] = callMF$2(p0, inner[i]);
        }
        return this;
    }
    @Override public Object mut$swap$2(Object p0, Object p1) {
        check(
          Long.compareUnsigned(Nat$c$0Instance.unwrap(p0), this.inner.length) < 0
            && Long.compareUnsigned(Nat$c$0Instance.unwrap(p1), this.inner.length) < 0,
          "EList.swap: index "+Nat$c$0Instance.unwrap(p0)
            + " is out of range for a EList of size "+this.xs.size()
        );
        swap(idx(p0, ".swap"), idx(p1, ".swap"));
        return this;
    }
    @Override public Object mut$shallowClone$0() {
        return new SparseArray(this);
    }


}
