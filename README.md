##Experiments with pdfbox 2.0.0 
---------------------------------

###Goal: 
----------

Produce a simplified flat representation of a PDF: A collection of graphics paths, images and text. Text is a hierarchical sequence of pages where each page is a collection of paragraph -> lines -> words -> character. Each class has a bounding box defined in the `user space`.

------------

The main class for the purpose of testing is `edu.ist.psu.sagnik.research.pdfbox2playground.impl.ShowResults`. Run it with two arguments: 

1. The location of the PDF file. (default is `src/test/resources/LoremIpsum.pdf`)
  
2. The page number you want to process. (default is `0`)
 
This will produce five PDFs of the form `<*-page-*>-chars,words,lines,paragraphs,rasters and paths.pdf` in the directory of the input PDF. These PDFs are marked with rectangles and self explanatory.

Data models for `text`,`raster` and `path` are explained in respective packages.

###TODO
------------

Serialized output as JSON, avro etc.