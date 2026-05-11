from mrjob.job import MRJob
class MRForestFire(MRJob):
    def mapper(self, _, line):
        # Skip header if present
        if "month" in line:
            return
       # Split CSV line
        data = line.split(',')
        try:
            month = data[2] # Month column
            area = float(data[12]) # Burned area column
            yield month, area
        except (IndexError, ValueError):
            pass

    def reducer(self, month, areas):
        # Calculate average area for the month
        total_area = 0
        count = 0
        for area in areas:
             total_area += area
             count += 1
        yield month, (total_area / count)

if __name__ == '__main__':
    MRForestFire.run()
