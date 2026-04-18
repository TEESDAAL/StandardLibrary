package base;

import static base.Util.*;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public record UStr$0Instance(int[] val) implements UStr$0{
  private static final Pattern uCodeText= Pattern.compile("[0-9A-F]{1,6}(?: [0-9A-F]{1,6})*");
  private static final int[] empty= {};
  private static final int[] nl= {'\n'};
  private static final int[] tick= {'`'};
  private static final int[] quote= {'"'};
  private static final String strChars=
    "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+-*/=<>,.;:()[]{}`'\"!?@#$%^&_|~\\ \n";

  public static UStr$0 instance(int[] val){ return new UStr$0Instance(val); }
  public static UStr$0 instance(String val){ return new UStr$0Instance(val.codePoints().toArray()); }

  @Override public String toString(){ return "UStr["+strUCode(val)+"]"; }

  @Override public Object read$uStr$0(){ return this; }
  @Override public Object read$imm$0(){ return this; }

  @Override public Object imm$$plus$1(Object p0){
    return new UStr$0Instance(cat(val,((UStr$0Instance)p0).val));
  }
  @Override public Object imm$$or$1(Object p0){
    return new UStr$0Instance(cat(val,nl,((UStr$0Instance)p0).val));
  }
  @Override public Object imm$$xor$1(Object p0){
    return new UStr$0Instance(cat(val,tick,((UStr$0Instance)p0).val));
  }
  @Override public Object imm$$xor_xor$1(Object p0){
    return new UStr$0Instance(cat(val,quote,((UStr$0Instance)p0).val));
  }

  @Override public Object imm$$or$0(){ return new UStr$0Instance(cat(val,nl)); }
  @Override public Object imm$$xor$0(){ return new UStr$0Instance(cat(val,tick)); }
  @Override public Object imm$$xor_xor$0(){ return new UStr$0Instance(cat(val,quote)); }

  @Override public Object imm$isEmpty$0(){ return bool(val.length == 0); }
  @Override public Object imm$size$0(){ return Nat$0Instance.instance(val.length); }

  @Override public Object imm$escape$0(){ return Str$0Instance.instance(escapeText(val)); }

  @Override public Object imm$scalarHex$0(){ return Str$0Instance.instance(strUCode(val)); }

  @Override public Object imm$isStr$0(){ return bool(isStr(val)); }

  @Override public Object imm$strExact$0(){
    return isStr(val)
      ? optSome(Str$0Instance.instance(new String(val,0,val.length)))
      : optEmpty();
  }
  @Override public Object imm$flow$0(){
    return new Flow$1Instance(Arrays.stream(val).mapToObj(e->Nat$0Instance.instance((long)e)));
  }
  @Override public Object imm$equalRepr$1(Object p0){
    return bool(Arrays.equals(val,((UStr$0Instance)p0).val));
  }
  @Override public Object imm$hashRepr$0(){
    return Nat$0Instance.instance(Arrays.hashCode(val));
  }
  @Override public Object imm$u$1(Object p0){
    return new UStr$0Instance(cat(val,parseUCode(((Str$0Instance)p0).val())));
  }
  @Override public Object imm$joinStr$1(Object p0){
    Stream<Object> s= ((Flow$1Instance)p0).s();
    @SuppressWarnings("unchecked")
    List<UStr$0Instance> parts= (List<UStr$0Instance>)(Object)s.toList();
    return new UStr$0Instance(join(val,parts));
  }
  private static int[] cat(int[] a,int[] b){
    int size= Math.addExact(a.length,b.length);
    var res= new int[size];
    System.arraycopy(a,0,res,0,a.length);
    System.arraycopy(b,0,res,a.length,b.length);
    return res;
  }
  private static int[] cat(int[] a,int[] b,int[] c){
    int ab= Math.addExact(a.length,b.length);
    int size= Math.addExact(ab,c.length);
    var res= new int[size];
    System.arraycopy(a,0,res,0,a.length);
    System.arraycopy(b,0,res,a.length,b.length);
    System.arraycopy(c,0,res,ab,c.length);
    return res;
  }
  private static int[] join(int[] sep,List<UStr$0Instance> parts){
    if (parts.isEmpty()){ return empty; }
    int size= 0;
    for(int i= 0; i < parts.size(); i++){
      if (i != 0){ size = Math.addExact(size,sep.length); }
      size= Math.addExact(size,parts.get(i).val.length);
    }
    var res= new int[size];
    int off= 0;
    for(int i= 0; i<parts.size(); i++){
      if (i!=0){
        System.arraycopy(sep,0,res,off,sep.length);
        off += sep.length;
      }
      var cur= parts.get(i).val;
      System.arraycopy(cur,0,res,off,cur.length);
      off += cur.length;
    }
    return res;
  }
  private static boolean isStr(int[] cps){
    for(int cp: cps){ if (!isStr(cp)){ return false; } }
    return true;
  }

  private static boolean isStr(int cp){
    return cp < 128 && strChars.indexOf((char)cp) != -1;
  }
  private static String strUCode(int[] cps){
    if (cps.length==0){ return ""; }
    var res= new StringBuilder();
    for(int i= 0; i<cps.length; i++){
      if (i!=0){ res.append(' '); }
      appendHex(res,cps[i]);
    }
    return res.toString();
  }
  private static void appendHex(StringBuilder res,int cp){
    String s= Integer.toHexString(cp).toUpperCase();
    for(int i= s.length(); i < 4; i++){ res.append('0'); }
    res.append(s);
  }
  private static int[] parseUCode(String s){
    if (s.isEmpty()){ return empty; }
    if (!uCodeText.matcher(s).matches()){ throw err("Malformed UStr.u input: "+s); }
    String[] parts= s.split(" ");
    var res= new int[parts.length];
    for(int i= 0; i<parts.length; i++){
      int cp;
      try{ cp= Integer.parseInt(parts[i],16); }
      catch(NumberFormatException e){ throw err("Malformed UStr.u input: "+s); }
      if (cp > 0x10FFFF || (0xD800 <= cp && cp <= 0xDFFF)){
        throw err("Malformed UStr.u input: "+s);
      }
      res[i]= cp;
    }
    return res;
  }
  private static String escapeText(int[] cps){
    if (cps.length == 0){ return "``.u"; }
    if (isStr(cps)){ return safeChunk(cps)+".u"; }
    var res= new StringBuilder();
    int i= 0;
    boolean first= true;
    while(i < cps.length){
      String term;
      if (isStr(cps[i])){
        int j= i + 1;
        while (j < cps.length && isStr(cps[j])){ j++; }
        var safe= Arrays.copyOfRange(cps,i,j);
        term= safeChunk(safe) + ".u";
        if (j < cps.length){
          int k= j + 1;
          while (k < cps.length && !isStr(cps[k])){ k++; }
          var unsafe= Arrays.copyOfRange(cps,j,k);
          term += "`"+strUCode(unsafe)+"`";
          i = k;
        }
        else{ i= j; }
      }
      else{
        int j= i + 1;
        while (j < cps.length && !isStr(cps[j])){ j++; }
        var unsafe= Arrays.copyOfRange(cps,i,j);
        term= "``.u`"+strUCode(unsafe)+"`";
        i= j;
      }
      if (first){ res.append(term); first= false; }
      else{ res.append("+(").append(term).append(")"); }
      }
    return res.toString();
  }


  private static String safeChunk(int[] cps){
    String s= new String(cps,0,cps.length);
    return ((Str$0Instance)Str$0Instance.instance(s).imm$escape$0()).val();
  }
}