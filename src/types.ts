export interface IPrinter {
  name: string;
  ip: string;
  mac: string;
  target: string;
  bt: string;
  usb: string;
}

export type PrinerEvents =
  | 'onDiscoveryDone'
  | 'onPrintSuccess'
  | 'onPrintFailure';

export type EventListenerCallback = (printers: IPrinter[]) => void;

export type PrinterSeriesName =
  | 'EPOS2_TM_M10'
  | 'EPOS2_TM_M30'
  | 'EPOS2_TM_P20'
  | 'EPOS2_TM_P60'
  | 'EPOS2_TM_P60II'
  | 'EPOS2_TM_P80'
  | 'EPOS2_TM_T20'
  | 'EPOS2_TM_T60'
  | 'EPOS2_TM_T70'
  | 'EPOS2_TM_T81'
  | 'EPOS2_TM_T82'
  | 'EPOS2_TM_T83'
  | 'EPOS2_TM_T88'
  | 'EPOS2_TM_T90'
  | 'EPOS2_TM_T90KP'
  | 'EPOS2_TM_U220'
  | 'EPOS2_TM_U330'
  | 'EPOS2_TM_L90'
  | 'EPOS2_TM_H6000'
  | 'EPOS2_TM_T83III'
  | 'EPOS2_TM_T100'
  | 'EPOS2_TM_M30II'
  | 'EPOS2_TS_100'
  | 'EPOS2_TM_M50';

export interface IPrinterInitParams {
  target: string;
  seriesName: PrinterSeriesName;
}
