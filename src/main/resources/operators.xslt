<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema">
<xsl:output method="text" version="1.0" encoding="UTF-8"/>

<xsl:template match="/">
  ---------------------
  XMLMath 1.1 Operator Reference
  ---------------------
  Erik van Zijst
  erik@xmlmath.org
  ---------------------
  May 2006
  ---------------------

<xsl:for-each select="//xs:element[@substitutionGroup='abstract-list'] |
//xs:element[@substitutionGroup='value'] |
//xs:element[@substitutionGroup='number'] |
//xs:element[@substitutionGroup='abstract-long'] |
//xs:element[@substitutionGroup='abstract-double'] |
//xs:element[@substitutionGroup='abstract-string'] |
//xs:element[@substitutionGroup='abstract-boolean']">
<xsl:call-template name="operator">
<xsl:with-param name="name" select="@name"/>
<xsl:with-param name="type" select="@type"/>
</xsl:call-template>
</xsl:for-each>
  
</xsl:template>

<xsl:template name="operator">
<xsl:param name="name" select="1"/>
<xsl:param name="type" select="1"/>
\&lt;<xsl:value-of select="$name"/>\&gt;

  extends: <xsl:value-of select="//xs:element[@name=$name]/@substitutionGroup"/>

  * &lt;&lt;Description&gt;&gt;

    <xsl:value-of select="//xs:complexType[@name=$type]/xs:annotation/xs:documentation"/>

  * &lt;&lt;Attributes&gt;&gt;
  
*------*------*----------*---------*--------------+
| &lt;&lt;Name&gt;&gt; | &lt;&lt;Type&gt;&gt; | &lt;&lt;Required&gt;&gt; | &lt;&lt;Default&gt;&gt; | &lt;&lt;Description&gt;&gt;
*----------+-------+-------+-------+-------+
|<xsl:for-each select="//xs:complexType[@name=$type]/@*">
<xsl:call-template name="attributeList">
<xsl:with-param name="attrib" select="name"/>
<xsl:with-param name="type" select="$type"/>
</xsl:call-template>
</xsl:for-each>
 
  * Arguments
  
*------*------*----------*---------*--------------*
| &lt;&lt;Type&gt;&gt; | &lt;&lt;Required&gt;&gt; | &lt;&lt;Min&gt;&gt; | &lt;&lt;Max&gt;&gt; | &lt;&lt;Description&gt;&gt;
*----------+-------+-------+-------+-------+
| number   | yes          | 2       | unbounded | These numbers are multiplied.
*----------+-------+-------+-------+-------+

=======================
</xsl:template>

<xsl:template name="attributeList">
<xsl:param name="attrib" select="1"/>
<xsl:param name="type" select="1"/>
| <xsl:value-of select="$type"/> | |  |  | 
*----------+-------+-------+-------+-------+
</xsl:template>

<xsl:template name="argumentList">
</xsl:template>

</xsl:stylesheet>
<!--
$ java -jar lib/saxon8.jar src/main/resources/xmlmath.xsd src/main/resources/operators.xslt |tee operators.txt && aptconvert -nonum -toc operators.html operators.txt
-->
