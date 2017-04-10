def list=['docs/README.html', 'docs/groovydoc/DefaultPackage/DocWalker.html','docs/groovydoc/DefaultPackage/package-frame.html',
'docs/groovydoc/overview-summary.html','docs/links.html','docs/sample.html','reports/coverage/default/DocWalker$_closure1$_closure4.html']

public class Cell{
    String r = ""
    // .size() = actual counts
    def r1=[] // addressed by base-zero offsets
    boolean rb 
    int rs
    boolean yn = false;
    
    public Cell(String w)
    {
        r = w;
        r1 = r.split(/\//) // split by /
        rb = (r1[r1.size() - 1].indexOf('.') > -1) ? true : false; // checks final token for filename syntax is with a dot
        rs = (rb) ? r1.size() - 1 : r1.size();
        println "r=${r} has ${r1.size()} r1.size() tokens rs=$rs rb=$rb;"
    } // end of constructor
    
    // ok but does not give correct number of = signs yet
    String getTitle(int t)
    {
        if (t==2)
        {
            def aa = '\n' 
            aa+= "=" * t;
            return aa;
        }    
        else
            return "=" * t;
    } // end of method

    public void say(tx)
    {
        println tx;
    } // end of method

    /*
     *
     */
    public buildFrom(Cell c)
    {
        boolean flag = false;
        def ad="";
        int ct = 0;
        
        r1.eachWithIndex{e, ix ->
            boolean dot = (e.indexOf('.') > -1 ) ? true : false;
            ct = ix + 2;
            if (ix < c.rs && !flag && !dot)
            {
                if (!yn && e.equals(c.r1[ix])) 
                { 
                    ad += getTitle(ct);
                    println "\ne equals "+e; 
                    ad += " 1 ${e.capitalize()} Folder yn=$yn\n\n"; 
                } 
                else 
                { 
                    ad += getTitle(ct);
                    flag=true; 
                    ad+=" 2 ${e.capitalize()} Folder  ix=$ix yn=$yn flag=$flag dot=$dot ct=$ct\n\n"; 
                } // end of if yn
        
                println "s1 ix="+ix+" c.rs="+c.rs+" ="+e+" and yn=$yn and c.r1="+c.r1[ix];
                say "yes\n"
            }        
            else
            {
                yn = true;
                flag = true;
                println "no ix="+ix+" and e="+e;
                if (!dot)
                {
                    ad += getTitle(ct);
                    ad+=" 3 ${e.capitalize()} Folder ix=$ix yn=$yn flag=$flag dot=$dot ct=$ct\n\n";
                }
                else
                { 
                    ad+=" * link:${e}[trash] ix=$ix yn=$yn flag=$flag dot=$dot ct=$ct\n";
                }       
            } // end of else    
        } // end of each    
        
        return ad;
    } // end of method
    
} // end of Cell


Cell b = new Cell('');
//Cell c = new Cell('DocWalker.adoc') //DoctorPepperX/src/main/groovy/DocWalker.adoc')
//Cell s = new Cell('DocWalker$_closure1$_closure4.html') //DoctorPepperX/reports/coverage/default/DocWalker$_closure1$_closure4.html')           //"DoctorPepperX/src/main/groovy/org/jnorthr/Talker.groovy"


def payload = "= Folder Contents\n\n== Structure\n\n";

list.sort().each{f->
    Cell x = new Cell(f);
    payload +=  x.buildFrom(b);
    b = x;
} // end of each

/*
payload +=  c.buildFrom(b);
payload += s.buildFrom(c);
b = new Cell('src/main/groovy/Fred.groovy');
payload += b.buildFrom(s);
*/
//--------------

println "\n-------------------------"
println payload.toString();
println "-------------------------\n--- the end ---"