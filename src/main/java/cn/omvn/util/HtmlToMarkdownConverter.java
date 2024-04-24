package cn.omvn.util;

public class HtmlToMarkdownConverter {

    public static String convertHtmlToMarkdown(String html) {
        String markdown = html;

        // Convert headers
        markdown = markdown.replaceAll("<h1>(.*?)</h1>", "# $1\n");
        markdown = markdown.replaceAll("<h2>(.*?)</h2>", "## $1\n");
        markdown = markdown.replaceAll("<h3>(.*?)</h3>", "### $1\n");

        // Convert paragraphs
        markdown = markdown.replaceAll("<p>(.*?)</p>", "$1\n\n");

        // Convert links
        markdown = markdown.replaceAll("<a\\s+href=\"([^\"]*)\".*?>(.*?)</a>", "[$2]($1)");

        // Convert images
        markdown = markdown.replaceAll("<img\\s+.*?src=\"([^\"]*)\".*?alt=\"([^\"]*)\".*?>", "![$2]($1)");

        // Remove line breaks and excess whitespace
        markdown = markdown.replaceAll("\\n\\s+\\n", "\n\n");

        // Remove any other HTML tags
        markdown = markdown.replaceAll("<[^>]*>", "");

        // Trim whitespace at the end of the document
        markdown = markdown.trim();

        return markdown;
    }

}