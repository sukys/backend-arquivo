package br.com.southsytem.fileprocessor.entrypoint;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

import br.com.southsytem.fileprocessor.entity.Sale;
import br.com.southsytem.fileprocessor.entity.Salesman;
import br.com.southsytem.fileprocessor.exceptions.BusinessException;
import br.com.southsytem.fileprocessor.service.ClientService;
import br.com.southsytem.fileprocessor.service.SaleService;
import br.com.southsytem.fileprocessor.service.SalesmanService;
import br.com.southsytem.fileprocessor.util.FileUtil;

/**
 * Class responsible for grouping all the information
 * 
 * @author luis
 *
 */
public class EntryPoint {

	private SalesmanService salesmanService;

	private SaleService saleService;

	private ClientService clientService;

	public EntryPoint() {
		this.salesmanService = new SalesmanService();
		this.saleService = new SaleService();
		this.clientService = new ClientService();
	}

	/**
	 * Process the file received extracting its contents and writing the statistics
	 * to a new File.
	 * 
	 * @param file
	 */
	public void processFile(File file) {
		this.resetData();
		try {
			LocalDateTime start = LocalDateTime.now();
			for (String record : FileUtil.readFileContent(file)) {
				processRecord(record);
			}
			salesmanService.associateSalesWithSalesman();
			String statistics = this.createStatisticsInfo();
			LocalDateTime end = LocalDateTime.now();
			Duration duration = Duration.between(start, end);
			String content = createProcessInfo(file.getName(), duration.toMillis()).concat(statistics);
			FileUtil.createFile(file.getName().replaceAll(".dat", ".done.dat"), content);
			file.delete();
		} catch (BusinessException e) {
			System.err.println(e.getMessage() + ". Aborting.");
			FileUtil.moveFileToErrorDir(file);
		}
	}

	/**
	 * Reset All data in order to process a new File.
	 */
	private void resetData() {
		this.salesmanService.resetDatabase();
		this.saleService.resetDatabase();
		this.clientService.resetDatabase();
	}

	/**
	 * Process a single line from recording, redirecting the data to be processed
	 * according its code.
	 * 
	 * @param record
	 */
	private void processRecord(String record) {
		String[] data = record.split("ç");
		switch (data[0]) {
		case "001":
			salesmanService.convertAndSave(data);
			break;

		case "002":
			clientService.convertAndSave(data);
			break;

		case "003":
			saleService.convertAndSave(data);
			break;

		default:
			System.err.println("Code [" + data[0] + "] unrecognized. Ignoring.");
			break;
		}
	}

	/**
	 * Create the text wit the statistics of the file processed.
	 * 
	 * @return
	 */
	private String createStatisticsInfo() {
		StringBuilder builder = new StringBuilder();
		Sale maiorVenda = this.saleService.getGreatestSale();
		Salesman piorVendedor = this.salesmanService.getWorstSalesman();
//		Salesman melhorVendedor = this.salesmanService.getBestSalesman();

		builder.append("\n-----------------------------------------------------");
		builder.append("\nTotal de Clientes...........: ");
		builder.append(this.clientService.count());
		builder.append("\nTotal de Vendedores.........: ");
		builder.append(this.salesmanService.count());
		builder.append("\nVenda mais cara.............: ");
		builder.append("Sale Id: ");
		builder.append(maiorVenda.getSaleId());
//		builder.append(" (R$ ");
//		builder.append(new DecimalFormat("#.##").format(maiorVenda.getTotal()));
//		builder.append(").");
		builder.append("\nPior Vendedor...............: ");
		builder.append(piorVendedor.getName());
//		builder.append("\nMelhor Vendedor.............: ");
//		builder.append(melhorVendedor.getName());
		builder.append("\n-----------------------------------------------------");
		builder.append("\n");

		return builder.toString();

	}

	/**
	 * Generates the info about the process.
	 * 
	 * @param filename
	 * @param processingTime
	 * @return
	 */
	private String createProcessInfo(String filename, long processingTime) {
		String data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		StringBuilder builder = new StringBuilder();
		Double time = ((processingTime / 1000.0) % 60.0);
		
		builder.append("\n-----------------------------------------------------");
		builder.append("\nData do Processamento.......: ");
		builder.append(data);
		builder.append("\nArquivo processado..........: ");
		builder.append(filename);
		builder.append("\nArquivo gerado..............: ");
		builder.append(filename.replace(".dat", ".done.dat"));
		builder.append("\nTempo do processamento......: ");
		builder.append(time);
		builder.append(" segundos");
		return builder.toString();
	}

}
