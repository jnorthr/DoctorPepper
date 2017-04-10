// parms: within folder y/n; folder depth:0-9; right-most value:filename;
def String[] ls = ["New.groovy","Old.groovy","DoctorPepper/src/docs/asciidoc/Walker.adoc", "DoctorPepper/src/docs/asciidoc/Finder.adoc", 
"DoctorPepper/src/Walker.groovy","DoctorPepper/src/main/groovy/Fred.groovy","DoctorPepperX/src/main/groovy/Max.groovy","DoctorPepperX/src/main/groovy/jnorthr/Jim.groovy",
"DoctorPepperX/src/main/groovy/jnorthr/Tools.groovy"] 

// -------------------------------------------------
// R holds prior path+filename
def r = "DoctorPepperX/src/docs/asciidoc/Walker.adoc"
def a = "Talker.adoc"      //"DoctorPepperX/src/docs/asciidoc/html/Talker.adoc"

def foldernames = -1;
r1=[]
printFolderStructure(r);
printFolderStructure(r,2);

def div(String r)
{
    r1 = r.split(/\//) // split by /
    println "r=${r} has ${r1.size()} tokens;" 

    // does final token have a dot for filenames ?
    boolean k = (r1[r1.size()-1].indexOf('.') > -1)?true:false;
    foldernames = (k) ? r1.size()-1 : r1.size() ;
    println "ends with filename token ? = ${k} with ${foldernames} folder names and all tokens are :"

    r1.eachWithIndex{x,ix-> 
        println ix+"="+x; 
    } // end of each
    println "--------------------------" 
    return foldernames;       
} // end of div

int w = div(r);
println "w="+w+"\n";

// s is current file
def s = "DoctorPepperX/src/main/groovy/Walker.groovy"

def printFolderStructure(def q)
{
    def q1 = q.split(/\//) // split by /
    def txt = ""
    int ctr = 2;
    
    q1.eachWithIndex{x,ix-> 
        if (q1[ix].indexOf('.') < 0)
        {  
            txt += "${'=' * ctr } ${q1[ix].capitalize()} Folder\n\n";
        }    
        ctr+=1;
    } // end of each

    println "\nprintFolderStructure\n+++++++++++++++++++++++++"
    println txt.toString();
    println "+++++++++++++++++++++++++"
} // end of method

// only print folders not above this level
def printFolderStructure(def q, int m)
{
    def q1 = q.split(/\//) // split by /
    def txt = ""
    int ctr = 2;
    
    q1.eachWithIndex{x,ix-> 
        if (q1[ix].indexOf('.') < 0 && ix >= m)
        {  
            txt += "${'=' * ctr } ${q1[ix].capitalize()} Folder\n\n";
        }    
        ctr+=1;
    } // end of each

    println "\nprintFolderStructure from lvl ${m}\n+++++++++++++++++++++++++"
    println txt.toString();
    println "+++++++++++++++++++++++++"
} // end of method


// only find which level folders become different
def findChangeOfFolderStructure(def r, def s)
{
    println "\nfindChangeOfFolderStructure\n+++++++++++++++++++++++++"
    println "r="+r;
    println "s="+s;

    def r1 = r.split(/\//) // split by /
    def s1 = s.split(/\//) // split by /

    def rs = r1.size();
    if (r1[rs - 1].indexOf('.') > -1) {rs -= 1; }
    def ss = s1.size();
    if (s1[ss - 1].indexOf('.') > -1) {ss -= 1; }
    println "r1.size()=${r1.size()} rs=$rs and s1.size()=${s1.size()} ss=$ss"


    int chg = -1;

    s1.eachWithIndex{x,ix-> 
        if(ix<rs){println "ix=$ix  r1=${r1[ix]} s1=${s1[ix]} "}
        if (s1[ix].indexOf('.') > -1)
        {  
            chg = ix
        }    
    } // end of each

    println "chg folder level is "+chg;
    println "+++++++++++++++++++++++++"
} // end of method

findChangeOfFolderStructure(a,s);




//-------------------------------
def String chop(String s)
{
    println "--------------------------"        
    def s1 = s.split(/\//) // split by /
    print "s=${s} has ${s1.size()} tokens;" 

    // does final token have a dot for filenames ?
    boolean y = (s1[s1.size()-1].indexOf('.') > -1)?true:false;
    int foldercount = (y) ? s1.size()-1 : s1.size() ;
    println " does final token have a dot for filenames ? ${y} so there are ${foldercount} folders"
    
    // used to generate multiple = signs for a given folder
    equalctr=2;
    sb=""
    
    // set this true when first non-equal folder name found
    boolean ne = false;

    s1.eachWithIndex{x,ix-> 
        boolean b = (ix==s1.size() -1 )?true:false;
        print "s1 ix="+ix+" and b="+b+"; ";
        if ( !ne &&  r1[ix].equals(s1[ix]) )  // ix > foldernames &&
            println "${r1[ix]}.equals(${s1[ix]})"
        else
        {
            ne = true;
            println "${r1[ix]}.NOTequals(${s1[ix]})"; // ${r1[ix]}.
            if (b) // final token is a filename not a folder
            {
                sb += " * ${s1[ix]}[xxx]"
            }
            else
            {  
                sb += "${'=' * equalctr } ${s1[ix].capitalize()} Folder\n\n";
            }
        } // end of else  
    
        equalctr+=1;
    } // end of each

    r1 = s1;
    return sb.toString()
} // end of chop


/*
def payload = chop(s);
println "\n+++++++++++++++++++++++++"
println payload;
println "+++++++++++++++++++++++++"

// t is current file
ls.each{e->
    payload = chop(e);
    println "\n+++++++++++++++++++++++++"
    println payload;
    println "+++++++++++++++++++++++++"
} // end of each
*/
println "-- the end -"