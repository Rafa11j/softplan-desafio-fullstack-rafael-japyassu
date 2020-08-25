import moment from 'moment';

const formatDate = (date: Date | string | undefined): string | null => {
  if (date === undefined) {
    return null;
  }
  return moment(date).format('DD/MM/yyyy');
};

export default formatDate;
