package com.gs.indexer;

import com.gs.crawler.PagePOJO;

public class Hit {
        public Hit(PagePOJO pagePOJO, long startOffset,String filename) {
                this.pagePOJO = pagePOJO;
                this.startOffset = startOffset;
                this.fileName = filename;
        }
        public Hit(PagePOJO pagePOJO, long startOffset,String filename,String clazz) {
            this.pagePOJO = pagePOJO;
            this.startOffset = startOffset;
            this.fileName = filename;
            this.clazz = clazz;
    }
        public Hit() {
        }
        private PagePOJO pagePOJO;
        private long startOffset;
        private String fileName;
        private String clazz;
        /**
		 * @return the clazz
		 */
		public String getClazz() {
			return clazz;
		}
		/**
		 * @param clazz the clazz to set
		 */
		public void setClazz(String clazz) {
			this.clazz = clazz;
		}
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
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Hit ["
					+ (pagePOJO != null ? "pagePOJO=" + pagePOJO + ", " : "")
					+ "startOffset=" + startOffset + ", "
					+ (fileName != null ? "fileName=" + fileName + ", " : "")
					+ (clazz != null ? "clazz=" + clazz : "") + "]";
		}
        
        
}