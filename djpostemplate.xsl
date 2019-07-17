<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited by XMLSpy® -->
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="TransactionData">
<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">

<fo:layout-master-set>
 	<fo:simple-page-master master-name="simple"
                page-height="29.7cm" page-width="21cm" margin-left="0.2cm"
                margin-right="0.2cm" >
                <fo:region-body margin-top="0.3cm" margin-bottom="0.3cm" margin-left="0.3cm" margin-right="0.3cm" background-color="WhiteSmoke" />
                </fo:simple-page-master>
</fo:layout-master-set>
  
  <fo:page-sequence master-reference="simple" >
  
     <fo:flow flow-name="xsl-region-body" >
     <xsl:for-each select="./storeList/store">
     <fo:block font-family="Courier" font-size="13pt" padding-top="5pt" space-before.optimum="5pt" font-weight="normal" text-align="center" background-color="WhiteSmoke"
            color="Black">

 
   <xsl:value-of select="storeName" />
   
   </fo:block>
    
    <fo:block font-family="Courier" font-size="13pt" font-weight="normal"  text-align="center" background-color="WhiteSmoke"
            color="Black">

      <xsl:value-of select="storeaddress1" />
      </fo:block>
      
       <fo:block font-family="Courier" font-size="13pt" font-weight="normal"  text-align="center" background-color="WhiteSmoke"
                  color="Black">
      
            <xsl:value-of select="storeaddress2" />
      </fo:block>
       <fo:block font-family="Courier" font-size="13pt" font-weight="normal" text-align="center" background-color="WhiteSmoke"
                  color="Black">
      
            <xsl:value-of select="storeaddress3" />
      </fo:block>
       <fo:block font-family="Courier" font-size="13pt" font-weight="normal" text-align="center" background-color="WhiteSmoke"
                  color="Black">
      
            <xsl:value-of select="storephone" />
      </fo:block>
	
  
    


 </xsl:for-each> 
 
 <fo:block font-family="Courier" font-size="8pt" font-weight="normal" text-align="left" background-color="WhiteSmoke"
                   color="Black">
       
             ----------------------------------------------------------------------------------------------------------------------
       </fo:block>
           <xsl:for-each select="./transactionHeaderList/transactionheader"> 
        <fo:block font-family="Courier" font-size="10pt" text-align="left" font-weight="normal" space-before.optimum="5pt"  background-color="WhiteSmoke"
                          color="Black">
                      <fo:inline  padding-left="4pt"> Transaction ID :  </fo:inline> 
                    <fo:inline >   <xsl:value-of select="transactionid" /> </fo:inline> 
                     
       </fo:block>
       <fo:block font-family="Courier" font-size="10pt" font-weight="normal" space-before.optimum="2pt" text-align="left" background-color="WhiteSmoke"
                                 color="Black">
                             <fo:inline padding-left="4pt"> Transaction Date :  </fo:inline> 
                           <fo:inline>   <xsl:value-of select="transactiondate" /> </fo:inline> 
       </fo:block>
       <fo:block font-family="Courier" font-size="10pt" font-weight="normal" space-before.optimum="2pt" text-align="left" background-color="WhiteSmoke"
                                 color="Black">
                             <fo:inline padding-left="4pt"> Transaction Time :  </fo:inline> 
                           <fo:inline>   <xsl:value-of select="transactiontime" /> </fo:inline> 
       </fo:block>
       <fo:block font-family="Courier" font-size="10pt" font-weight="normal" space-before.optimum="2pt" text-align="left" background-color="WhiteSmoke"
                                 color="Black">
                             <fo:inline padding-left="4pt"> User Served :  </fo:inline> 
                           <fo:inline>   <xsl:value-of select="transactionuser" /> </fo:inline> 
       </fo:block>
	
	</xsl:for-each> 
       
    
       
         <fo:block font-family="Courier" font-size="11pt" text-align="center" font-weight="normal" space-before.optimum="13pt"  background-color="WhiteSmoke"
                                 color="Black">
                             SALE TRANSACTION
                            
       </fo:block>
       
       
     
         <fo:block-container space-before.optimum="20pt" absolute-position="auto"  left="40pt"  width="80%">
                
              <fo:block-container absolute-position="auto" left="40pt"  width="80%">

                
        <fo:block  font-family="Courier" font-size="10pt" padding-left="8pt" font-weight="normal" space-before.optimum="10pt" text-align="center" 
         color="Black">
 
                                      
       <fo:table border="solid 0.5mm black" start-indent="20pt" width="80%" >
       <fo:table-column column-width="45mm" />
        <fo:table-column column-width="45mm" />
       <fo:table-column column-width="45mm" />
       <fo:table-column column-width="45mm" />
       
       <fo:table-header>
         <fo:table-row   height="0.2cm" background-color="yellowgreen" >
           <fo:table-cell padding='20px' border="solid 0.3mm black">
             <fo:block font-weight="bold" font-size="10pt"  color="White">Item ID</fo:block>
           </fo:table-cell>
            <fo:table-cell padding='20px' border="solid 0.3mm black">
	                <fo:block font-weight="bold" font-size="10pt"  color="White" >Item Description</fo:block>
           </fo:table-cell>
           <fo:table-cell padding='20px' border="solid 0.3mm black">
	                <fo:block font-weight="bold" font-size="10pt" background-color="yellowgreen" color="White" >Quantity</fo:block>
           </fo:table-cell>
           <fo:table-cell padding='20px' border="solid 0.3mm black">
             <fo:block font-weight="bold" font-size="10pt" background-color="yellowgreen" color="White" >Price</fo:block>
           </fo:table-cell>
         </fo:table-row>
       </fo:table-header>

       <fo:table-body>
	 <xsl:for-each select="./transactionLineItemList/transactionLineItem">

         <fo:table-row  border="solid 0.3mm black" height="0.1cm">
           <fo:table-cell padding='15px'  border="solid 0.3mm black">
	             <fo:block>  <xsl:value-of select="itemID" /> </fo:block>
           </fo:table-cell>
          <fo:table-cell padding='15px'  border="solid 0.3mm black">
             <fo:block> <xsl:value-of select="itemDesc" /> </fo:block>
           </fo:table-cell>
           <fo:table-cell padding='15px'  border="solid 0.3mm black">
	             <fo:block>    <xsl:value-of select="itemQuantity" /> </fo:block>
           </fo:table-cell>
           <fo:table-cell padding='15px' border="solid 0.3mm black">
            <fo:block>  <xsl:value-of select="itemPrice" /> </fo:block>
           </fo:table-cell>
         </fo:table-row>
       
            </xsl:for-each>
       </fo:table-body>
        
       
       </fo:table>
      
       </fo:block>
       </fo:block-container>
           </fo:block-container>
       
       
       
       
    <!--    <fo:block-container position="auto" space-before.optimum="20pt"   left="300pt"  width="50%">
        
           <fo:block-container absolute-position="auto" left="40pt"  width="90%">
                              
              <fo:block font-family="Courier" font-size="10pt" padding-left="8pt" font-weight="normal"  text-align="center" background-color="WhiteSmoke"
               color="Black">
       
             
             <fo:table border="solid 0.5mm black"   >
             <fo:table-column column-width="45mm" />
              <fo:table-column column-width="45mm" />
           
             
        
             
             <fo:table-body>
               <fo:table-row   border="solid 0.3mm black" height="0.3cm">
                 <fo:table-cell padding='20px' border="solid 0.3mm black" text-align="center">
      	               <fo:block>Total</fo:block>
                 </fo:table-cell>
                <fo:table-cell padding='20px' border="solid 0.3mm black" text-align="right">
                   <fo:block>$98000</fo:block>
                 </fo:table-cell>
                
               </fo:table-row>
               
               <fo:table-row  border="solid 0.3mm black" height="0.3cm" >
                <fo:table-cell padding='20px' border="solid 0.3mm black" text-align="center">
	             	               <fo:block>Discount</fo:block>
	                        </fo:table-cell>
	                       <fo:table-cell padding='20px' border="solid 0.3mm black" text-align="right">
	                          <fo:block>0</fo:block>
                 </fo:table-cell>
               </fo:table-row>
               
                <fo:table-row  border="solid 0.3mm black" height="0.3cm">
	                       <fo:table-cell padding='20px' border="solid 0.3mm black" text-align="center">
	       	             	               <fo:block>Paid by</fo:block>
	       	                        </fo:table-cell>
	       	                       <fo:table-cell padding='20px' border="solid 0.3mm black" text-align="right">
	       	                          <fo:block>CASH</fo:block>
	                        </fo:table-cell>
               </fo:table-row>
               
             </fo:table-body>
             
             </fo:table>
             </fo:block>
              </fo:block-container>
                 </fo:block-container> -->
                   
                    <xsl:for-each select="./transactionTenderList/transactionTender">
                 
      <fo:block font-family="Courier" font-size="13pt" font-weight="normal" space-before.optimum="25pt" text-align="right" background-color="WhiteSmoke"
                                      color="Black">
                                  <fo:inline padding-right="60pt">Sub Total :</fo:inline> 
                                <fo:inline padding-right="45pt">     $<xsl:value-of select="transactionSubTotal" /> </fo:inline> 
       </fo:block>

       <fo:block font-family="Courier" font-size="13pt" font-weight="normal" space-before.optimum="5pt" text-align="right" background-color="WhiteSmoke"
                                             color="Black">
                                         <fo:inline padding-right="67pt">Discount :</fo:inline> 
                                       <fo:inline padding-right="45pt">     $<xsl:value-of select="transactionDiscount" /> </fo:inline> 
       </fo:block>
       
       
              <fo:block font-family="Courier" font-size="13pt" font-weight="normal" space-before.optimum="5pt" text-align="right" background-color="WhiteSmoke"
                                                    color="Black">
                                                <fo:inline padding-right="75pt">Paid by :</fo:inline> 
                                              <fo:inline padding-right="45pt">     <xsl:value-of select="transactionTenderType" /> </fo:inline> 
       </fo:block>
       
       
        <fo:block font-family="Courier" font-size="13pt" font-weight="normal" space-before.optimum="5pt" text-align="right" background-color="WhiteSmoke"
                                                    color="Black">
                                                <fo:inline padding-right="60pt">Total Paid :</fo:inline> 
                                              <fo:inline padding-right="45pt">      <xsl:value-of select="transactionTotal" />  </fo:inline> 
       </fo:block>
       
          </xsl:for-each>
       
        <fo:block font-family="Courier" font-size="8pt" space-before.optimum="25pt"  font-weight="normal" text-align="left" background-color="WhiteSmoke"
                          color="Black">
              
                    ----------------------------------------------------------------------------------------------------------------------
       </fo:block>
       
       <fo:block font-family="Courier" font-size="11pt" space-before.optimum="25pt"  font-weight="normal" text-align="center" background-color="WhiteSmoke"
                                 color="Black">
                     Thank you for visiting DJ Enterprise Solutions!
                           
       </fo:block>
       
       <fo:block font-family="Courier" font-size="11pt" space-before.optimum="5pt"  font-weight="normal" text-align="center" background-color="WhiteSmoke"
                                        color="Black">
                            Kindly Visit Again!
                                  
       </fo:block>
       
      
</fo:flow>

 

 </fo:page-sequence>
</fo:root>
</xsl:template>
</xsl:stylesheet>