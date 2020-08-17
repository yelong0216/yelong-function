/**
 * 
 */
package org.yelong.function.library.excel;

import java.io.File;

/**
 * @author PengFei
 *
 */
public interface ModelSheetGenerator {

	<M> File generate(SimpleSheetModel<M> sheetModel, String filePath) throws GenerateException;

}
