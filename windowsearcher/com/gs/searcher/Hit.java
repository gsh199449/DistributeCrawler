package com.gs.searcher;

import com.gs.searcher.PagePOJO;

public class Hit {
        public Hit(PagePOJO pagePOJO, long startOffset,String filename) {
                this.pagePOJO = pagePOJO;
                this.startOffset = startOffset;
                this.fileName = filename;
        }
        public Hit() {
        }
        private PagePOJO pagePOJO;
        private long startOffset;
        private String fileName;
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
		 * @return the fileName
		 */
		public String getFileName() {
			return fileName;
		}
		/**
		 * @param fileName the fileName to set
		 */
		public void setFileName(String fileName) {
			this.fileName = fileName;
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