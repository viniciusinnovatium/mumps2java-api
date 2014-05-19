package com.disclinc.netmanager.script.test;
//*****************************************************************************
//** TASC - ALPHALINC - MAC TesteNew
//** Innovatium Systems - Code Converter - v1.24
//** 2014-05-19 17:15:32
//*****************************************************************************

import mLibrary.*;


//<< ;#########################################################
//<< ;#     New all                                           #
//<< ;#########################################################
public class TesteNew extends mClass {

public void main() {
  //<< Set locVar = "1.0"
  mVar locVar = m$.var("locVar");
  locVar.set("1.0");
  //<< Set vetorLocal("xpto",34,"A")="xpto~34~A"
  mVar vetorLocal = m$.var("vetorLocal");
  vetorLocal.var("xpto",34,"A").set("xpto~34~A");
  //<< Set vetorLocal("xpto",34,"B")="xpto~34~B"
  vetorLocal.var("xpto",34,"B").set("xpto~34~B");
  //<< Set %varPublica(1,56)="eis o meu valor!"
  m$.var("%varPublica",1,56).set("eis o meu valor!");
  //<< Do labelNewAll
  m$.Cmd.Do("labelNewAll");
  //<< Write !!,"A partir daqui as variáveis voltam a estar visíveis",!,"===================================================",!
  m$.Cmd.Write("\n","\n","A partir daqui as variáveis voltam a estar visíveis","\n","===================================================","\n");
  //<< Write !,"locVar : >",locVar,"<"
  m$.Cmd.Write("\n","locVar : >",locVar.get(),"<");
  //<< Write !,"vetorLocal(""xpto"",34,""A"")  : >",vetorLocal("xpto",34,"A"),"<"
  m$.Cmd.Write("\n","vetorLocal(\"xpto\",34,\"A\")  : >",vetorLocal.var("xpto",34,"A").get(),"<");
  //<< Write !,"%varPublica(1,56) : >",%varPublica(1,56),"<"
  m$.Cmd.Write("\n","%varPublica(1,56) : >",m$.var("%varPublica",1,56).get(),"<");
  //<< Quit
  return;
}

//<< labelNewAll
public void labelNewAll() {
  //<< Write !!,"Inicialmente as variáveis estarão VISIVEIS     ",!,"===================================================",!
  m$.Cmd.Write("\n","\n","Inicialmente as variáveis estarão VISIVEIS     ","\n","===================================================","\n");
  //<< Write !,"locVar (Before New) : >",locVar,"<"
  m$.Cmd.Write("\n","locVar (Before New) : >",m$.var("locVar").get(),"<");
  //<< Write !,"vetorLocal(""xpto"",34,""A"") (Before New) : >",vetorLocal("xpto",34,"A"),"<"
  m$.Cmd.Write("\n","vetorLocal(\"xpto\",34,\"A\") (Before New) : >",m$.var("vetorLocal").var("xpto",34,"A").get(),"<");
  //<< Write !,"%varPublica(1,56) : >",%varPublica(1,56),"<"
  m$.Cmd.Write("\n","%varPublica(1,56) : >",m$.var("%varPublica",1,56).get(),"<");
  //<< New (zzz)
  mVar zzz = m$.var("zzz");
  m$.newVarExcept(zzz);
  //<< Write !!,"A partir daqui as variáveis estarão INVISIVEIS   -  Exceto as publicas (%)  ",!,"===================================================",!
  m$.Cmd.Write("\n","\n","A partir daqui as variáveis estarão INVISIVEIS   -  Exceto as publicas (%)  ","\n","===================================================","\n");
  //<< Set locVarAfterNew = $Get(locVar)
  mVar locVarAfterNew = m$.var("locVarAfterNew");
  locVarAfterNew.set(m$.Fnc.$get(m$.var("locVar")));
  //<< Merge vetorLocalAfterNew = vetorLocal
  mVar vetorLocalAfterNew = m$.var("vetorLocalAfterNew");
  mVar vetorLocal = m$.var("vetorLocal");
  m$.Cmd.Merge(vetorLocalAfterNew,vetorLocal);
  //<< Write !,"locVar (AfterNew) : >",locVarAfterNew,"<"
  m$.Cmd.Write("\n","locVar (AfterNew) : >",locVarAfterNew.get(),"<");
  //<< Write !,"vetorLocal (AfterNew) : >",$get(vetorLocalAfterNew("xpto",34,"A")),"<"
  m$.Cmd.Write("\n","vetorLocal (AfterNew) : >",m$.Fnc.$get(vetorLocalAfterNew.var("xpto",34,"A")),"<");
  //<< Write !,"%varPublica(1,56) (AfterNew) : >",$G(%varPublica(1,56)),"<"
  m$.Cmd.Write("\n","%varPublica(1,56) (AfterNew) : >",m$.Fnc.$get(m$.var("%varPublica",1,56)),"<");
  //<< 
  //<< Quit
  return;
}

}
