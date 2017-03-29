sb = "="
def gen(String key,int val, boolean yn,def st){
        if (yn)
        {
            sb+= "link:../${st}[${key}]\n"
        }
        else
        switch(val)
        {
            case 0: sb+="= ${key} folder\n\n";
                    break;
            case 1: sb+="== ${key} folder\n\n";
                    break;
            case 2: sb+="=== ${key} folder\n\n";
                    break;
            case 3: sb+="==== ${key} folder\n\n";
                    break;
            case 4: sb+="===== ${key} folder\n\n";
                    break;
            case 5: sb+="====== ${key} folder\n\n";
                    break;
            case 6: sb+="======= ${key} folder\n\n";
                    break;
            case 7: sb+="======== ${key} folder\n\n";
                    break;
            default: sb += "\n${key} folder\n\n" 
                    break;
        };
} // end of gen

// r is prior file
def r = "DoctorPepper/src/docs/asciidoc/Walker.adoc"
println r;
def r1 = r.split(/\//) // split by /
println "number of r1 tokens="+r1.size() 

def sz = r1.size() - 1
println "-----\nr1 folders=${sz}"

sb = "="
r1.eachWithIndex{x,ix-> 
    boolean b = (ix==sz)?true:false;
    gen(x,ix,b,r) 
} // end of each
        
        println "+++++++++++++++++++++++++"
        println sb.toString()    
        println "+++++++++++++++++++++++++"

//println "-----------------------\n"


// s is current file
def s = "DoctorPepper/src/main/groovy/Walker.groovy"
println s;
def s1 = s.split(/\//) // split by /
//def w =  s.split() // split by whitespace

println "number of s1 tokens="+s1.size() 

//println "size of whitespace tokens="+w.size()

r1.eachWithIndex{x,ix-> println ix+"="+x; } // println ix+"="+x; 

sz = s1.size() - 1
println "-----\nx=${sz}"
s1.eachWithIndex{x,ix-> 
    boolean b = (ix==sz)?true:false;
    gen(x,ix,b,s) 
}

boolean tf = true;
int count = 0;

if (r1.size()==s1.size())
{
    println "\n-equals texting-"
    r1.eachWithIndex{x,ix-> 
        if (x.equals(s1[ix])&&tf) 
        {
            count+=1;
            println "'${x}'='${s1[ix]}'";
        } 
        else 
        {
            tf=false;
            println "${x}!=${s1[ix]}";} 
        } // end of else
        
} // end of if
println "\ncount="+count;

println "-- the end -"