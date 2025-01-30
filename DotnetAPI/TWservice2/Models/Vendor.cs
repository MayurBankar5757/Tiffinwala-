using System;
using System.Collections.Generic;

namespace TWservice2.Models
{
    public partial class Vendor
    {
        public Vendor()
        {
            VendorSubscriptionPlans = new HashSet<VendorSubscriptionPlan>();
        }

        public int VendorId { get; set; }
        public bool IsVerified { get; set; }
        public int Uid { get; set; }
        public string? AdharNo { get; set; }
        public string? FoodLicenceNo { get; set; }

        public virtual User UidNavigation { get; set; } = null!;
        public virtual ICollection<VendorSubscriptionPlan> VendorSubscriptionPlans { get; set; }
    }
}
