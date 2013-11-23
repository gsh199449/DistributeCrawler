package com.gs.indexer;

import com.gs.crawler.PagePOJO;

public class Hit {
        public Hit(PagePOJO pagePOJO, long startOffset) {
                this.pagePOJO = pagePOJO;
                this.startOffset = startOffset;
        }
        public Hit() {
        }
        private PagePOJO pagePOJO;
        private long startOffset;
        /**
         * @return the pagePOJO
         */
        public PagePOJO getPagePOJO() {
                return pagePOJO;
        }
        /**
         * @param pagePOJO the pagePOJO to set
         */
        public void setPagePOJO(PagePOJO pagePOJO) {
                this.pagePOJO = pagePOJO;
        }
        /**
         * @return the startOffset
         */
        public long getStartOffset() {
                return startOffset;
        }
        /**
         * @param startOffset the startOffset to set
         */
        public void setStartOffset(long startOffset) {
                this.startOffset = startOffset;
        }
        /**
         * @return the endOffset
         */
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
                return "Hit ["
                                + (pagePOJO != null ? "pagePOJO=" + pagePOJO + ", " : "")
                                + "startOffset=" + startOffset + "]";
        }
        
        
}