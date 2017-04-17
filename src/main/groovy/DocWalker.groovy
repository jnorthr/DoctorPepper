// groovy code to choose one folder to walk thru the files found within it
//package io.jnorthr.toolkit;

// **************************************************************
import java.io.File;
import java.io.IOException;

//import java.util.regex.Pattern
import java.util.concurrent.TimeUnit;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Files;
import java.nio.file.Path;

/**
* The DocWalker program implements a support application that examines a single folder directory for html files and then
* steps thru that folder. For each .html file, it's name and title create one entry in asciidoctor toc.adoc file 
*
* Initially starts to choose artifacts from directory given in program arg[0] and saves 
* selected files in a local asciidoctor text file whose folder target is arg[1];
*
*
* @author  jnorthr
* @version 1.0
* @since   2017-03-22
*/
public class DocWalker
{       
    /**
     * List to hold user selected files.
     */
    def files = []
    
    /**
     * Keeps name of folder being traversed.
     */
    String fn = '~';

    /**
     * Keeps name of output folder + toc.adoc being written to.
     */
    String ofn = '/Users/jimnorthrop/Dropbox/Projects/DoctorPepper/src/docs/asciidoc/toc.adoc';

    /**
     * Count of selected target files in folder being traversed.
     */
    int count = 0;
    

    /**
     * Count of charaters in name of folder being traversed.
     */
    int size = 0;
    
    
   // =========================================================================
   /** 
    * Class constructor.
    *
    * default constructor is private as user must provide name of directory as constructor string
    */
    private DocWalker()
    {
    } // end of constructor


   // =========================================================================
   /** 
    * Non-default class constructor.
    *
    * @param pathname String holds directory name given in program arg[0] to choose artifacts from
    * @param outputFolder String holds directory name given in program arg[1] where to write new toc.adoc.
    */
    public DocWalker(String pathname, String outputFolder)
    {
        if ( new File(pathname).exists() &&  new File(pathname).isDirectory() && new File(outputFolder).exists() &&  new File(outputFolder).isDirectory() )
        {
            this.fn = pathname.trim();
            this.size  = this.fn.size()
            this.ofn = outputFolder + File.separator + "toc.adoc";
        }
        else
        {
            //throw new RuntimeException
            println("... DocWalker directory ${pathname} does not exist or is not a directory") 
            System.exit(0);
        } // end of else        
    } // end of non-default constructor



   // -------------------------------------------------------        
   /** 
    * Closure.
    *
    * defaults to walking the folder looking for files that match a RegEx value
    */
    Closure findTxtFileClos = {
        it.eachDir(findTxtFileClos);
        it.eachFileMatch(~/.*.html/) {file ->
                boolean yes = (file.name.startsWith('.')) ? false :true;
                if (yes)
                {
                    files << file;
                }
        }
    } // end of closure

    
   /**
    * Method to examine the chosen folder.
    */
    public void run()
    {
    	println "... ${fn} has ${size} characters";
        findTxtFileClos(new File(fn))
        count = 0;
        
        // construct initial prefix of asciidoctor document
        def sb ="= Folder Table of Contents\n:icons: font\n\n== Folder Location\n\n=== ${fn}\n\n"
        
        // look thru each file
        files.toSorted().each
        {
        	// get full filename
            def ss = it.toString().trim();
            
            // logic to winkle out title from html text & place into 'tl'
            def tx = it.text;
            int m = tx.indexOf("<title>")
            int n = tx.indexOf("</title>")
            def tl = (m > -1)? tx.substring(m+7,n) :"no title";
            
			// take filename only
            int k = ss.lastIndexOf('/')
            def na = ss.substring(size+1)

			// do not include the toc again
            if (!na.equals("toc.html"))
            {
                count++;
                
                // construct asciidoctor file link syntax
                sb += " * link:../"+ na+"[$tl] - ${na}; ";
                
                // ck to see if a matching PDF file exists
                if (ss.toLowerCase().endsWith(".html"))
                {
                	m = ss.size()
                	tx = ss.substring(0,m-5)+".pdf"
                	if (new File(tx).exists())
                	{
                		// yes, PDF exists so insert a link for it after link to html file
	                	tx = na.substring(0,na.size()-5)+".pdf"
                		sb += " link:../"+ tx+"[pdf]"
                	} // end of if
                } // end of if
                
                sb += "\n" 
            } // end of if

        } // end of each
        
		// insert count of file included here
        sb += "\n''''\n\nTIP: Found $count files\n\n''''\n"
        
        println "... writing "+ofn;        
        new File(ofn).withWriter('UTF-8') { writer ->
            writer.write( sb.toString() )
        } // end of writer

    } // end of run



   /** 
    * See if toc.adoc date is not too recent else gradle -t creates a loop, 
    * so wait at least 120 seconds before writing a new table of contents: toc.adoc file
    * that becomes toc.html in build/docs folder on next run of asciidoctor gradle task
    *
     @return boolean indicating true if walker should scan input folder and write new toc.adoc.
    */
    public boolean checker()
    {
    	boolean yes = true;

	if ( new File(ofn).exists() )
	{
		Path filePath = new File(ofn).toPath();			
    	    	BasicFileAttributes attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
		def diff = System.currentTimeMillis() - attributes.lastAccessTime().to(TimeUnit.MILLISECONDS); // .creationTime()  
            	yes = (diff > 120000) ?true:false;
            	println "... milleseconds diff since last toc created =${diff} so do we build a new toc? ="+yes;
        } // end of if
        
        return yes;
    } // end of checker

   /** 
    * Produce messages
    *
    * @param msg String of text to be written to system output
    */
    public void say(String msg)
    {
        println msg;
    } // end of say   
    
    
    // =============================================================================    
    /**
     * The primary method to execute this class. Can be used to test and examine logic and performance issues. 
     * 
     * argument is a list of strings provided as command-line parameters. 
     * 
     * @param  args a list of possibly zero entries from the command line
     */
    public static void main(String[] args)
    {
        DocWalker dw;
        //dw = new DocWalker('/Users/jimnorthrop/Dropbox/Projects/DoctorPepper/build/docs');
        
        /*
         * need to test using 2 args: arg1 is folder name holding html files to be included in toc
         * arg2 is folder name where toc.adoc will be written
         */
        if (args.size() > 1) 
        { 
            println "... DocWalker reading path args[0]="+args[0];
            dw = new DocWalker( args[0], args[1] );
            if ( dw.checker() ) { dw.run(); }
        }
        else
        {  
            println "... DocWalker needs starting folder to be searched";
            System.exit(0);
        } // end of 
        
        //System.exit(0);
    } // end of main    
    
} // end of class